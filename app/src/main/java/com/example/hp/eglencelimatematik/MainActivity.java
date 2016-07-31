package com.example.hp.eglencelimatematik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    int  random = (int) (Math.random() *10 );
    Class[] classes = {Bir.class,Iki.class,Uc.class,Dort.class,Bes.class,Alti.class,Yedi.class,Sekiz.class,Dokuz.class,On.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Kategoriler");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan_action));



    }
public void islemleriAc(View view){
    intent = new Intent(this, IslemlerMainActivity.class);
    startActivity(intent);
}

    public void sayilariAc(View view) {

       intent = new Intent(this, classes[random]);
        startActivity(intent);
    // intent = new Intent(this, Bir.class);
      //  startActivity(intent);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        super.onDestroy();
    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
        finish();

        }

        return super.onKeyDown(keyCode, event);

    }
}


