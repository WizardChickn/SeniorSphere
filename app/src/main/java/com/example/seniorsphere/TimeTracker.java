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

    long current;


    /*
    *starts counting time
    */
    public void startStopwatch() {
        if (!running) {
        running = true;
         //   handler.post(runnable);
        }
        current = System.currentTimeMillis();
        System.out.println("yo"+System.currentTimeMillis());
    }

    /*
    *stops counting time and stores the data
    */
    public void stopStopwatch() {
       running = false;
       // wasRunning = false;
       // handler.removeCallbacks(runnable);
        seconds = (int)(System.currentTimeMillis()-current)/1000;
        //System.out.println(System.currentTimeMillis());
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

        return seconds;
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
