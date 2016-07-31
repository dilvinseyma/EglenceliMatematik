package com.example.hp.eglencelimatematik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Score extends AppCompatActivity {
    TextView scoreView, scoreTView;
    Button btnCont,btnExit;
String scoreStr;
    int score;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PUANLAR");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan_action));

        Typeface myTypeface= Typeface.createFromAsset(getAssets(), "station.ttf");
        btnCont=(Button)findViewById(R.id.btnDevam);
        btnCont.setTypeface(myTypeface);
        btnExit=(Button)findViewById(R.id.btnCikis);
        btnExit.setTypeface(myTypeface);

        Typeface myTypeface1= Typeface.createFromAsset(getAssets(), "scoreText.ttf");
        scoreTView = (TextView) findViewById(R.id.scoreYaziView);
        scoreTView.setTypeface(myTypeface1);

        Typeface myTypeface2= Typeface.createFromAsset(getAssets(), "playtime.otf");
        scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setTypeface(myTypeface2);

        SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        //key değerini vererek value değerini alacak
        score= sharedPreferences.getInt("score",0);
        scoreStr=Integer.toString(score);

        scoreView=(TextView) findViewById(R.id.scoreView);
        scoreView.setText(scoreStr);

        btnCont=(Button)findViewById(R.id.btnDevam);
        btnExit=(Button)findViewById(R.id.btnCikis);

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent=new Intent(getApplicationContext(),IslemlerDevamActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
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
    }
}
