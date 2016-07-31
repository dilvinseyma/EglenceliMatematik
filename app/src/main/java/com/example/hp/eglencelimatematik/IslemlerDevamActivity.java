package com.example.hp.eglencelimatematik;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class IslemlerDevamActivity extends AppCompatActivity implements  TextToSpeech.OnInitListener {
    protected static final int RESULT_SPEECH = 1;
    private TextToSpeech tts;
    private String name;
    private String msj = "Hangi işlemi yapmak istersin ?";
    private Thread thread, thread1;
    private String islemismi;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islemler_devam);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Eğlenceli Matematik");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan_action));


        tts = new TextToSpeech(this, this);
        speakOut(msj);

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(1500);
                        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
                        try {
                            startActivityForResult(intent, RESULT_SPEECH);
                        } catch (ActivityNotFoundException a) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Cihazınız bu uygulamayı desteklemiyor..", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                } catch (InterruptedException ex) {

                }

                //TODO
            }
        };

        thread.start();

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("tr", "TR");
            int result = tts.setLanguage(locale);//deneme

            //normalde olan //int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut(msj);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    islemismi = text.get(0);
                    if (islemismi.equals("bölme")) {
                        intent = new Intent(getApplicationContext(), com.example.hp.eglencelimatematik.BolmeSeviye.class);
                        startActivity(intent);
                    }
                    if (islemismi.equals("çarpma")) {
                        intent = new Intent(getApplicationContext(), com.example.hp.eglencelimatematik.CarpmaSeviye.class);
                        startActivity(intent);
                    }
                    if (islemismi.equals("toplama")) {
                        intent = new Intent(getApplicationContext(), com.example.hp.eglencelimatematik.ToplamaSeviye.class);
                        startActivity(intent);
                    }
                    if (islemismi.equals("çıkarma")) {
                        intent = new Intent(getApplicationContext(), CikarmaSeviye.class);
                        startActivity(intent);
                    }
                }
       break;

        }
    }

}
