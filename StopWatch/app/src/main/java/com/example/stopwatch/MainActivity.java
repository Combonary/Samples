package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView timerTV;
    Button startButton, stopButton;
    long time;
    int trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTV = findViewById(R.id.time_textView);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> startCounter());

        stopButton = findViewById(R.id.stop_button2);
        stopButton.setOnClickListener(v -> stopCounter());

        time = 0;
        trigger = 0;
        Calendar calendar = new GregorianCalendar();
        Log.d(TAG, "onCreate: " + "MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
        System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));

    }

    private void startCounter() {
        Runnable timer = new Runnable() {
            @Override
            public void run() {

                trigger = 0;
                do {
                    time += 1000;

                    String formattedTime = formatTime(time);
                    timerTV.post(() -> {
                        /*formatTime(time);
                        String formattedTime = String.format("%.3f" , time);*/
                        timerTV.setText("" + formattedTime);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "run: ");
                } while (trigger == 0);
            }
        };
        new Thread(timer).start();

    }

    private void stopCounter() {
        trigger = 1;
    }

    private String formatTime(long durationInMillis) {
        long millis = durationInMillis % 1000;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d:%03d", hour, minute, second, millis);
    }
}