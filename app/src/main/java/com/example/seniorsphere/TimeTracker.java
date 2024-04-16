package com.example.seniorsphere;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//keeps track of seconds and if the stopwatch is running
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

    //starts running the stopwatch
    public void startStopwatch() {
        if (!running) {
            running = true;
            handler.post(runnable);
        }
    }

    //stops running the stopwatch
    public void stopStopwatch() {
        running = false;
        wasRunning = false;
    }

    //resets the stopwatch
    public void resetStopwatch() {
        running = false;
        wasRunning = false;
        seconds = 0;
    }

    //saves the state of a stopwatch
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    //restores the state of an activity
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

    //pauses stopwatch
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    //resumes stopwatch
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            startStopwatch();
        }
    }

}
