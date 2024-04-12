package com.example.seniorsphere;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TimeTracker extends AppCompatActivity{
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (running) {
                seconds++;
            }
            handler.postDelayed(this, 1000);
        }
    };
    public void startStopwatch() {
        if (!running) {
            running = true;
            handler.post(runnable);
        }
    }

    public void stopStopwatch() {
        running = false;
        wasRunning = false;
    }

    public void resetStopwatch() {
        running = false;
        wasRunning = false;
        seconds = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        seconds = savedInstanceState.getInt("seconds");
        running = savedInstanceState.getBoolean("running");
        wasRunning = savedInstanceState.getBoolean("wasRunning");
        if (wasRunning) {
            startStopwatch();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            startStopwatch();
        }
    }

}
