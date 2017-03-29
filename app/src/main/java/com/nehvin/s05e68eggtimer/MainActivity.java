package com.nehvin.s05e68eggtimer;


import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ImageView eggBrown = null;
    SeekBar timerSeekBar = null;
    Button startStopButton = null;
    TextView countDownText = null;
    long minutes=0;
    long seconds=30;
    CountDownTimer cd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAll();


        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                // log progress in 1 second intervals upto 10 minutes

                minutes = ((progress / 1000) / 60);
                seconds = ((progress / 1000) % 60);
//                minutes = TimeUnit.MILLISECONDS.toMinutes(progress);
//                seconds = TimeUnit.MILLISECONDS.toSeconds(progress);
                Log.i(Long.toString(minutes), Long.toString(seconds));
                if(minutes==0 && seconds==0)
                {
                    startStopButton.setEnabled(false);
                }
                else
                {
                    startStopButton.setEnabled(true);
                }
                if(progress!=600000) {
                    secondsSetting();
                }
                else{
                    countDownText.setText("10:00");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void secondsSetting() {
        if(seconds<10)
        {
            countDownText.setText(Long.toString(minutes) + ":0" + Long.toString(seconds));
        }
        else
        {
            countDownText.setText(Long.toString(minutes) + ":" + Long.toString(seconds));
        }
    }

    private void initializeAll()
    {
        eggBrown = (ImageView)findViewById(R.id.eggBrown);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600000);
        timerSeekBar.setProgress(30000);
        startStopButton = (Button) findViewById(R.id.startStopButton);
        startStopButton.setEnabled(true);
        countDownText = (TextView) findViewById(R.id.countDownText);
        countDownText.setText("0:30");
    }

    public void startCountdown(View view)
    {
        String buttonLabel = startStopButton.getText().toString();

        if("Go".equalsIgnoreCase(buttonLabel))
        {
            Log.i(Long.toString(minutes), Long.toString(seconds));
            timerSeekBar.setEnabled(false);
            startStopButton.setText("Stop");
            long t = (minutes * 60L) + seconds;
            long countDownTimer = TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
            Log.i("Countdown milliseconds", Long.toString(countDownTimer));
            cd = new CountDownTimer(countDownTimer + 100, 1000){
                @Override
                public void onTick(long millisecondsToFinish) {
                    minutes = ((millisecondsToFinish / 1000) / 60);
                    seconds = ((millisecondsToFinish / 1000) % 60);
                    secondsSetting();
                }

                @Override
                public void onFinish() {
                    countDownText.setText("00:00");
                    MediaPlayer.create(getApplicationContext(),R.raw.airhorn).start();
                    reinitializeCounters();
                }
            }.start();
        }
        else
        {
            Log.i("Stop Timer", buttonLabel);
            cd.cancel();
            reinitializeCounters();
        }
    }

    private void reinitializeCounters() {
        startStopButton.setText("Go");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30000);
        countDownText.setText("00:30");
        minutes=0;
        seconds=30;
    }
}