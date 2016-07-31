package com.example.hp.eglencelimatematik;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Toplama extends AppCompatActivity implements MediaPlayer.OnCompletionListener, TextToSpeech.OnInitListener{

    protected static final int RESULT_SPEECH = 1;
    int sayi1, sayi2, dogruDeger, dogruSecenek,aralik;
    int yanlisDeger1,yanlisDeger2;
    Button btn1, btn2, btn3;
    TextView txtIslem;
    String islemMetni;
    Random r = new Random();
    private TextToSpeech tts;
    private MediaPlayer mediaPlayer = null;
    private Thread thread,mythread, yourthread;
    int[] dizi = new int[]{R.raw.applause3,R.raw.wrong};// 1.secenek dogru sesi,2.secenek yanlış
    String soruText;
    int count,score;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplama);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TOPLAMA");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan_action));

        tts= new TextToSpeech(this,this);

        Typeface myTypeface= Typeface.createFromAsset(getAssets(), "sketch.otf");
        btn1=(Button)findViewById(R.id.btnSecenek1);
        btn1.setTypeface(myTypeface);
        btn2=(Button)findViewById(R.id.btnSecenek2);
        btn2.setTypeface(myTypeface);
        btn3=(Button)findViewById(R.id.btnSecenek3);
        btn3.setTypeface(myTypeface);

        Typeface myTypeface1= Typeface.createFromAsset(getAssets(), "pwchildren.ttf");
        txtIslem = (TextView) findViewById(R.id.textViewIslemler);
        txtIslem.setTypeface(myTypeface1);

        islemleriYap();

       mythread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(2000);
                        speakOut(soruText);
                    }
                }
                catch(InterruptedException ex){
                }

                //TODO
            }
        };
        mythread.start();
        thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(7000);
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
                        try {
                            startActivityForResult(intent, RESULT_SPEECH);
                        } catch (ActivityNotFoundException a) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Cihazınız bu uygulamayı desteklemiyor..", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }
                catch(InterruptedException ex){
                }

                //TODO
            }
        };

        thread.start();
    }
    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onInit(int status) {
// TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("tr", "TR");
            int result = tts.setLanguage(locale);//deneme


//normalde olan //int result = tts.setLanguage(Locale.US);

// tts.setPitch(5); // set pitch level

tts.setSpeechRate(-3); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                 //speakOut(soruText);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

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

    public void islemleriYap()
    {
        if (ToplamaSeviye.kolayBasildiMi==true){
            sayi1 = r.nextInt(6);
            sayi2 = r.nextInt(6);
            aralik =11;

        }
        else if (ToplamaSeviye.ortaBasildiMi==true){
        sayi1 = r.nextInt(16)+10;
        sayi2 = r.nextInt(16)+10;

           aralik=51;
    }
        else{
            sayi1 = r.nextInt(25)+26;
            sayi2 = r.nextInt(25)+26;
            aralik=101;
        }
        // toplamaseviyeden gelen degerler alınacak artık rastgele yerine



        String sayi1text = String.valueOf(sayi1);
        String sayi2text= String.valueOf(sayi2);

        dogruSecenek = r.nextInt(3);

        yanlisDeger1 = r.nextInt(aralik);
        yanlisDeger2 = r.nextInt(aralik);
        dogruDeger = islemiHesapla(sayi1, sayi2);

        while (dogruDeger == yanlisDeger1)
        {
            yanlisDeger1 = r.nextInt(aralik);
        }
        while (dogruDeger == yanlisDeger2 || yanlisDeger1 == yanlisDeger2)
        {
            yanlisDeger2 = r.nextInt(aralik);
        }
        islemMetni = String.valueOf(sayi1) + " + " + String.valueOf(sayi2) + " = ?";
        txtIslem.setText(islemMetni);

        soruText = sayi1text+" artı "+sayi2text +" işleminin cevabı aşağıdakilerden hangisidir ?";
       // speakOut(soruText);


        switch (dogruSecenek)
        {
            case 0:
                btn1.setText(String.valueOf(dogruDeger));
                btn2.setText(String.valueOf(yanlisDeger1));
                btn3.setText(String.valueOf(yanlisDeger2));
                break;
            case 1:
                btn2.setText(String.valueOf(dogruDeger));
                btn1.setText(String.valueOf(yanlisDeger1));
                btn3.setText(String.valueOf(yanlisDeger2));
                break;
            case 2:
                btn3.setText(String.valueOf(dogruDeger));
                btn1.setText(String.valueOf(yanlisDeger1));
                btn2.setText(String.valueOf(yanlisDeger2));
                break;
        }
    }

    public int islemiHesapla(int sayi1, int sayi2) {
        return sayi1 + sayi2;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        score= sharedPreferences.getInt("score",0);
        switch (requestCode)
        {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null)
                {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (text.get(0).equals(String.valueOf(dogruDeger)) || (text.get(0).equals("bir") && dogruDeger == 1))
                    {
                        score+=10;
                        SharedPreferences sharedPreferences1 = getSharedPreferences("mydocs",
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        // editör aracılığı ile key value değerleri yazılacak

                        editor.putInt("score",score);

                        //bilgileri kaydedecek
                        editor.commit();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.applause3);
                        mediaPlayer.start();

                    } else
                    {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                        mediaPlayer.start();
                    }
                }
        }
        sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        //key değerini vererek value değerini alacak
        count= sharedPreferences.getInt("count",0);
if(count<3) {
    yourthread = new Thread() {
        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait(1500);
                    count++;
                    SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    // editör aracılığı ile key value değerleri yazılacak

                    editor.putInt("count", count);

                    //bilgileri kaydedecek
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), Toplama.class);
                    startActivity(intent);

                }
            } catch (InterruptedException ex) {
            }

            //TODO
        }
    };
    yourthread.start();

}
        else{
    Intent intent = new Intent(getApplicationContext(), Score.class);
    startActivity(intent);
        }



    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}


