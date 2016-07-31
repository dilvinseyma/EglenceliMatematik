
package com.example.hp.eglencelimatematik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class ToplamaSeviye extends AppCompatActivity {

   public static Button kolayBtn, ortaBtn,zorBtn;
    Random r = new Random();
   public static int sayi1, sayi2;
    Intent intent;
    public static boolean kolayBasildiMi=false,ortaBasildiMi=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplama_seviye);

        kolayBtn = (Button)findViewById(R.id.btnKolay);
        ortaBtn= (Button)findViewById(R.id.btnOrta);
        zorBtn= (Button) findViewById(R.id.btnZor);

        kolayBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

               kolayBasildiMi=true;
                ortaBasildiMi=false;
                intent = new Intent(getApplicationContext(), Toplama.class);
                startActivity(intent);
            }
        });
        ortaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ortaBasildiMi=true;
                kolayBasildiMi=false;
                intent = new Intent(getApplicationContext(), Toplama.class);
                startActivity(intent);

            }
        });
       zorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kolayBasildiMi=false;
                ortaBasildiMi=false;
                intent = new Intent(getApplicationContext(), Toplama.class);
                startActivity(intent);

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        // dosyaya yazmaya yardımcı olacak editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editör aracılığı ile key value değerleri yazılacak

        editor.putInt("count",1);
        editor.putInt("score",0);

        //bilgileri kaydedecek
        editor.commit();
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toplama_seviye, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
