package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = this.getIntent();
        String nameString;
        Button orderDish=(Button) findViewById(R.id.order_dish_btn);
        Button checkOrder=(Button) findViewById(R.id.check_order_btn);

        nameString = intent.getStringExtra("FromEntry");
        if(nameString!=null){
            if(!nameString.equals("FromEntry")){
                orderDish.setVisibility(View.INVISIBLE);
                checkOrder.setVisibility(View.INVISIBLE);
            }
        }

        nameString=intent.getStringExtra("LoginSuccess");
        if(nameString!=null){
            if(nameString.equals("LoginSuccess")){
                if(orderDish.getVisibility()==View.INVISIBLE){
                    orderDish.setVisibility(View.VISIBLE);
                }
                if(checkOrder.getVisibility()==View.INVISIBLE){
                    checkOrder.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void onLoginRegister(View v){
        Intent intent=new Intent(MainScreen.this, LoginOrRegister.class);
        startActivity(intent);
    }
}
