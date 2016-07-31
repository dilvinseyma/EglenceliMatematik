package com.example.hp.eglencelimatematik;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Bir extends AppCompatActivity implements TextToSpeech.OnInitListener{

    protected static final int RESULT_SPEECH = 1;
    private TextToSpeech tts;
    private String msj="Bu sayının kaç olduğunu biliyor musun";
    private Thread thread,thread1,thread2;
    String sayi;
    Intent intent;
    private Button nextBtn;
    int  random = (int) (Math.random() *10 );
    Class[] classes = {Bir.class,Iki.class,Uc.class,Dort.class,Bes.class,Alti.class,Yedi.class,Sekiz.class,Dokuz.class,On.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bir);

        nextBtn=(Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getApplicationContext(),classes[random]);
                startActivity(intent);
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sayıları Öğren");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan_action));

        tts = new TextToSpeech(this, this);
        speakOut(msj);
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3500);
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
                        try {
                            // speakOut(msj);
                            startActivityForResult(intent, 1);
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

    public void nextNumber(View view){

        Intent intent = new Intent(this, classes[random]);
        startActivity(intent);
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (text.get(0).equals(String.valueOf(1)) || (text.get(0).equals("bir"))) {
                        speakOut("Aferin doğru bildin");
                        nextNum();

                    } else {

                        speakOut("tekrar dene");
                        thread1 = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(1500);
                                        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
                                        try {
                                            startActivityForResult(intent, 2);
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
                        thread1.start();
                    }

                }
                break;
            case 2:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (text.get(0).equals(String.valueOf(1))) {
                        speakOut("Aferin doğru bildin");
                        nextNum();
                    } else {
                        speakOut("tekrar dene");
                        thread2= new Thread() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(1500);
                                        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
                                        try {
                                            startActivityForResult(intent, 3);
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
                        thread2.start();
                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (text.get(0).equals(String.valueOf(1))) {
                        speakOut("Aferin doğru bildin");
                        nextNum();
                    } else {
                        speakOut("Bu sayı Bir");

                        nextNum();

                    }
                }
                break;


        }

    }
    public void nextNum(){
        thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                        Intent intent = new Intent(getApplicationContext(), classes[random]);
                        startActivity(intent);
                    }
                }
                catch(InterruptedException ex){
                }

                // TODO
            }
        };

        thread.start();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            System.exit(0);

        }

        return super.onKeyDown(keyCode, event);

    }
}
