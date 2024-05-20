package com.example.seniorsphere;
import android.os.Handler;

import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
/*
* measures time using a stopwatch
*/

public class TimeTracker extends AppCompatActivity{
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (running) {
                seconds++;
            } else{
                handler.removeCallbacks(this);
            }
            //handler.postDelayed(this, 1000);
        }
    };

    /*
    *starts running the stopwatch
    */
    public void startStopwatch() {
        if (!running) {
            running = true;
            handler.post(runnable);
        }
    }

    /*
    *stops running the stopwatch
    */
    public void stopStopwatch() {
        running = false;
        wasRunning = false;
        handler.removeCallbacks(runnable);
    }

    /*
    *resumes the stopwatch
    *stops running and seconds are set to 0
    */
    public void resetStopwatch() {
        running = false;
        wasRunning = false;
        seconds = 0;
        handler.removeCallbacks(runnable);
    }

    /*
    *saves the state of the stopwatch
    */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    /*
    *restores the state of an activity
    */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        seconds = savedInstanceState.getInt("seconds");
        running = savedInstanceState.getBoolean("running");
        wasRunning = savedInstanceState.getBoolean("wasRunning");
        if (wasRunning) {
            startStopwatch();
        }
    }

    /*
    *pauses the stopwatch
    */
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;

        handler.removeCallbacks(runnable);
    }

    @Override
    /*
    *resumes the stopwatch
    */
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            startStopwatch();
        }
    }

    /*
    *returns the number of minutes
    *@return minutes
    */
    public int getMinutes(){

        return seconds/60;
    }

    /*
    *returns the number of seconds
    *@return seconds
    */
    public int getTime(){

        return seconds*60;
    }

    /*
    *returns the number of hours 
    *@return hours
    */
    public int getHours(){

        return seconds/3600;
    }

    /*
    *returns if the stopwatch is running or not
    *@return running
    */
    public boolean getRunning(){
        return running;
    }

}
