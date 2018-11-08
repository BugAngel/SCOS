package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import es.source.code.service.UpdateService;
import es.source.code.utils.FoodInformation;
import es.source.code.utils.Global;

public class SCOSEntry extends Activity {

    private float x1;//手指按下时X坐标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
    }

    /**
     * 重写activity的onTouch方法
     * 监听滑动事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){ //手指按下
            x1=event.getX();
        }
        if(event.getAction()==MotionEvent.ACTION_UP){ //手指抬起
            float x2;//手指抬起时X坐标
            x2=event.getX();
            if(x1-x2>100){
                String data ="FromEntry";
                Intent intent=new Intent(SCOSEntry.this, MainScreen.class);
                intent.putExtra("FromEntry",data);
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);
    }
}
