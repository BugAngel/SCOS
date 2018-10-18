package es.source.code.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import es.source.code.model.User;

public class LoginOrRegister extends Activity {

    private int progress = 0;
    private Timer timer;
    private TimerTask timerTask;
    private String username;
    private String passwd;
    private Button login_button = null;
    private Button register_button = null;
    private EditText username_edit = null;
    //获取sharedPreferences对象
    SharedPreferences sharedPreferences = null;
    //获取editor对象
    SharedPreferences.Editor editor = null;//获取编辑器
    private ProgressDialog progressDialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        username_edit = findViewById(R.id.username_edit);
        sharedPreferences = getSharedPreferences("WRSCOS", Context.MODE_PRIVATE);

        // SharedPreferences 中是否有用户名
        //getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
        String name = sharedPreferences.getString("userName", "");
        if(name.equals("")){
            login_button.setVisibility(View.INVISIBLE);
        }else{
            register_button.setVisibility(View.INVISIBLE);
            username_edit.setText(name);
        }
    }

    //点击登录
    public void onLogin(View v){
        //    弹出要给ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在登录中，请稍后......");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为水平的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        startTimer();

        if(checkUsernamePassword()){
            editor=sharedPreferences.edit();
            //存储键值对
            editor.putString("userName", username_edit.getText().toString());
            editor.putInt("loginState", 1);
            editor.apply();//提交修改

            User loginUser=new User();
            loginUser.setUserName(username);
            loginUser.setPassword(passwd);
            loginUser.setOldUser(true);

            String data ="LoginSuccess";
            Intent intent=new Intent(LoginOrRegister.this, MainScreen.class);
            intent.putExtra("LoginSuccess",data);
            intent.putExtra("user",loginUser);
            startActivity(intent);
        }
    }

    //返回按键
    public void onGoBack(View v){
        //getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
        String name = sharedPreferences.getString("userName", "");
        if(!name.equals("")){
            editor=sharedPreferences.edit();
            editor.putInt("loginState", 0);
            editor.apply();//提交修改
        }

        String data ="Return";
        Intent intent=new Intent(LoginOrRegister.this, MainScreen.class);
        intent.putExtra("Return",data);
        startActivity(intent);
    }

    private void startTimer() {
    //如果timer和timerTask已经被置null了
        if (timer == null&&timerTask==null) {
            //新建timer和timerTask
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    //每次progress加一
                    progress++;
                    //如果进度条满了的话就让停止定时器
                    if (progress >= 100) {
                        endTimer();
                    }
                    //设置进度条进度
                    progressDialog.setProgress(progress);
                }
            };
            /*开始执行timer,第一个参数是要执行的任务，
            第二个参数是开始的延迟时间（单位毫秒）或者是Date类型的日期，代表开始执行时的系统时间
            第三个参数是计时器两次计时之间的间隔（单位毫秒）*/
            timer.schedule(timerTask, 0, 20);
        }
    }

    private void endTimer(){
        timerTask.cancel();
        timer.cancel();
        timerTask=null;
        timer=null;
        progressDialog.dismiss();
    }

    public void onRegister(View v){
        if(checkUsernamePassword()){
            User loginUser=new User();
            loginUser.setUserName(username);
            loginUser.setPassword(passwd);
            loginUser.setOldUser(false);

            if(checkUsernamePassword()){
                editor=sharedPreferences.edit();
                editor.putString("userName", username_edit.getText().toString());
                editor.putInt("loginState", 1);
                editor.apply();//提交修改
            }

            String data ="RegisterSuccess";
            Intent intent=new Intent(LoginOrRegister.this, MainScreen.class);
            intent.putExtra("RegisterSuccess",data);
            intent.putExtra("user",loginUser);
            startActivity(intent);
        }
    }

    //验证登录名和登录密码是否符合规则
    private boolean checkUsernamePassword(){
        EditText username_edit;
        EditText passwd_edit;

        username_edit= findViewById(R.id.username_edit);
        passwd_edit=findViewById(R.id.passwd_edit);
        username=username_edit.getText().toString();
        passwd=passwd_edit.getText().toString();
        String regex = "[A-Za-z0-9]+";
        if(username.matches(regex) && passwd.matches(regex)){
            return true;
        }else if(!username.matches(regex)){
            username_edit.setError("输入内容不符合规则");
        }else{
            passwd_edit.setError("输入内容不符合规则");
        }
        return false;
    }
}
