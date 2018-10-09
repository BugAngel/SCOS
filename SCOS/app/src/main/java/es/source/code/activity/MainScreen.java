package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.source.code.adapter.MainScreenAdapter;
import es.source.code.model.GridItem;
import es.source.code.model.User;

import static java.security.AccessController.getContext;

public class MainScreen extends Activity implements AdapterView.OnItemClickListener{
    private String[] mainscreen_name= new String[]{
            "登录/注册","系统帮助","点菜", "查看订单",
    };//选项名

    private int[] mainscreen_image=new int[]{
            R.drawable.login_register_btn,R.drawable.system_help_btn,
            R.drawable.order_dish_btn, R.drawable.check_order_btn
    };//图片

    private GridView mGridView;
    private final int LESS_SHOW=1;//只显示部分图标
    private final int ALL_SHOW=2;//只显示全部图标

    User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = this.getIntent();
        String nameString;

        /*默认全部显示*/
        mGridView = findViewById(R.id.grid_view);
        showAll();

        /*intent判断入口界面传值*/
        nameString = intent.getStringExtra("FromEntry");
        if(nameString!=null){
            if(!nameString.equals("FromEntry")){
                showLess();
            }
        }

        /*intent判断登录成功传值*/
        nameString=intent.getStringExtra("LoginSuccess");
        if(nameString!=null){
            if(nameString.equals("LoginSuccess")){
                showAll();
                user = (User)getIntent().getSerializableExtra("user");
            }
        }

        /*intent判断注册成功传值*/
        nameString=intent.getStringExtra("RegisterSuccess");
        if(nameString!=null){
            if(nameString.equals("RegisterSuccess")){
                showAll();

                Toast.makeText(this.getApplicationContext(), "欢迎您成为 SCOS 新用户", Toast.LENGTH_SHORT).show();
                user = (User)getIntent().getSerializableExtra("user");
            }
        }
    }

    /**
     * 获取GridView的数据
     */
    private ArrayList getGridViewData() {
        ArrayList list = new ArrayList<>();

        for (int i=0; i<mainscreen_name.length; i++) {
            GridItem item=new GridItem(mainscreen_image[i],mainscreen_name[i]);
            list.add(item);
        }

        return list;
    }

    /**
     * 获取隐藏点菜和查看订单的GridView的数据
     */
    private ArrayList getLessGridViewData() {
        ArrayList list = new ArrayList<>();

        for (int i=0; i<LESS_SHOW+1; i++) {
            GridItem item=new GridItem(mainscreen_image[i],mainscreen_name[i]);
            list.add(item);
        }

        return list;
    }

    /**
     * 隐藏点菜和查看订单
     */
    private void showLess(){
        MainScreenAdapter mainScreenAdapter=new MainScreenAdapter(this,getLessGridViewData(),LESS_SHOW);
        mGridView.setAdapter(mainScreenAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 显示导航栏
     */
    private void showAll(){
        MainScreenAdapter mainScreenAdapter=new MainScreenAdapter(this,getGridViewData(),ALL_SHOW);
        mGridView.setAdapter(mainScreenAdapter);
        mGridView.setOnItemClickListener(this);
    }
    /**
     * GridView的点击回调函数
     *
     * @param adapter  -- GridView对应的dapterView
     * @param view     -- AdapterView中被点击的视图(它是由adapter提供的一个视图)。
     * @param position -- 视图在adapter中的位置。
     * @param rowid    -- 被点击元素的行id。
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long rowid) {
        // 根据元素位置获取对应的值
        GridItem item = new GridItem(mainscreen_image[position],mainscreen_name[position]);

        Toast.makeText(this.getApplicationContext(), "You Select "+item.getName(), Toast.LENGTH_SHORT).show();

        if (item.getName().equals("登录/注册")) {
            Intent intent=new Intent(MainScreen.this, LoginOrRegister.class);
            startActivity(intent);
        }

        if (item.getName().equals("点菜")) {
            Intent intent=new Intent(MainScreen.this, FoodView.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }

        if (item.getName().equals("查看订单")) {
            Intent intent=new Intent(MainScreen.this, FoodOrderView.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
    }

}
