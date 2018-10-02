package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
    }

    public void onSCOS(View v){
        Intent intent = new Intent();
        intent.setClassName("es.source.code.activity", "es.source.code.activity.MainScreen");
        startActivity(intent);
    }
}
