package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class LoginOrRegister extends Activity {

    private int progress = 0;
    private ProgressBar pb;
    private Timer timer;
    private TimerTask timerTask;
    private boolean login_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        pb = findViewById(R.id.login_progressbar);
    }

    public void onLogin(View v){
        EditText username_edit;
        EditText passwd_edit;

        startTimer();
        //验证登录名和登录密码是否符合规则
        username_edit= findViewById(R.id.username_edit);
        passwd_edit=findViewById(R.id.passwd_edit);
        String username=username_edit.getText().toString();
        String passwd=passwd_edit.getText().toString();
        String regex = "[A-Za-z0-9]+";
        if(username.matches(regex) && passwd.matches(regex)){
            login_flag=true;
        }else if(!username.matches(regex)){
            username_edit.setError("输入内容不符合规则");
        }else{
            passwd_edit.setError("输入内容不符合规则");
        }
    }

    public void onGoBack(View v){
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
                    if (progress >= 200) {
                        endTimer();
                    }
                    //设置进度条进度
                    pb.setProgress(progress);
                }
            };
            /*开始执行timer,第一个参数是要执行的任务，
            第二个参数是开始的延迟时间（单位毫秒）或者是Date类型的日期，代表开始执行时的系统时间
            第三个参数是计时器两次计时之间的间隔（单位毫秒）*/
            timer.schedule(timerTask, 0, 10);
        }
    }

    private void endTimer(){
        timerTask.cancel();
        timer.cancel();
        timerTask=null;
        timer=null;
        pb.setVisibility(View.INVISIBLE);
        if(login_flag){
            String data ="LoginSuccess";
            Intent intent=new Intent(LoginOrRegister.this, MainScreen.class);
            intent.putExtra("LoginSuccess",data);
            startActivity(intent);
        }
    }
}
