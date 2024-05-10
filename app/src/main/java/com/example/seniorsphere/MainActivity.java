package com.example.seniorsphere;


import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    String name;
    EditText nameInput;
    Button submitButton;

    Bundle savedInstanceState;

    TimeTracker stratstopwatch = new TimeTracker();
    TimeTracker logicstopwatch = new TimeTracker();
    TimeTracker patternstopwatch = new TimeTracker();

    Boolean isHome = false;

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //super.onBackPressed();
        if (isHome) super.onBackPressed();

        else {

            if (stratstopwatch.getRunning()){
                stratstopwatch.stopStopwatch();
                editor.putFloat("time1", sharedPreferences.getFloat("time1", 0) + stratstopwatch.getMinutes());
            }
            if (logicstopwatch.getRunning()){
                logicstopwatch.stopStopwatch();
                editor.putFloat("time2", sharedPreferences.getFloat("time2", 0)+ logicstopwatch.getMinutes());
            }

            if (patternstopwatch.getRunning()){
                patternstopwatch.stopStopwatch();
                editor.putFloat("time3", sharedPreferences.getFloat("time3", 0)+ patternstopwatch.getMinutes());
            }


            toHome(savedInstanceState);
            editor.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        isHome=true;
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);
        onStart(savedInstanceState);
    }
    protected void onStart(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        name = sharedPreferences.getString("username", "defaultUsername");

        isHome=true;



        if (name.isEmpty()){
            setContentView(R.layout.welcome_screen);


            nameInput = findViewById(R.id.nameInput);

            submitButton = findViewById(R.id.submitButton);
            submitButton.setOnClickListener(v -> {
                name = nameInput.getText().toString();
                editor.putString("username", name);
                editor.putFloat("time1", 0);
                editor.putFloat("time2", 0);
                editor.putFloat("time3", 0);
                editor.commit();

                toHome(savedInstanceState);

            });}
        else toHome(savedInstanceState);

    }

    protected void toHome(Bundle savedInstanceState) {
        isHome=false;
        setContentView(R.layout.homescreen);

        TextView message= findViewById(R.id.motivationalMessage);
        message.setText(makeMessage());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        showToast("Welcome "+sharedPreferences.getString("username", "defaultUsername"));

//different buttons lead to different pages
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            logicstopwatch.resetStopwatch();
            logicstopwatch.startStopwatch();
            String url = "https://www.nytimes.com/games/wordle/index.html";
            loadWeb(R.layout.web_loader, url);
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            patternstopwatch.resetStopwatch();
            patternstopwatch.startStopwatch();
            String url = "https://www.nytimes.com/games/connections";
            loadWeb(R.layout.web_loader, url);
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            logicstopwatch.resetStopwatch();
            logicstopwatch.startStopwatch();
            String url = "https://sudoku.com/";
            loadWeb(R.layout.web_loader, url);
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> {
            stratstopwatch.resetStopwatch();
            stratstopwatch.startStopwatch();
            String url = "https://www.chess.com/";
            loadWeb(R.layout.web_loader, url);
        });

        Button moveToStats = findViewById(R.id.moveToStats);
        moveToStats.setOnClickListener(v -> onStats());

        Button moveToSettings = findViewById(R.id.moveToSettings);
        moveToSettings.setOnClickListener(v -> onSettings(savedInstanceState));
    }

    //loads embedded websites
    //numerous functions to prevent errors with loading in websites
    //javascript enabled to handle difficulties with websites
    protected void loadWeb(int web, String url) {
        isHome=false;
        setContentView(web);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Handle page start
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Handle page finish
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Overrides errors
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(url);
        webView.clearView();
        webView.measure(100, 100);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


    }

    public String makeMessage() {
        //make an arraylist of inspirational and positive messages
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Believe you can and you're halfway there.");
        messages.add("The only way to do great work is to love what you do.");
        messages.add("Success is not final, failure is not fatal: It is the courage to continue that counts.");
        messages.add("The future belongs to those who believe in the beauty of their dreams.");
        messages.add("Your limitation—it's only your imagination.");
        messages.add("The best time to plant a tree was 20 years ago. The second best time is now.");
        messages.add("Quit,don't quit... noodles, don't noodles");
        messages.add("Every accomplishment starts with the decision to try.");
        messages.add("Yesterday is history, tomorrow is a mystery, today is a gift.");

        //create a random thing
        Random hehe = new Random();
        //generate random number between 0 and arraylist.length - 1
        int index = hehe.nextInt(9);

        return messages.get(index);
    }

    protected void onStats(){
        isHome=false;
        //super.onCreate(savedInstanceState);

        setContentView(R.layout.stats);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        float stratminutes =  sharedPreferences.getFloat("time1", 0);//stratstopwatch.getMinutes();
        float logicminutes =  sharedPreferences.getFloat("time2", 0);//logicstopwatch.getMinutes();
        float patternminutes = sharedPreferences.getFloat("time3", 0);//patternstopwatch.getMinutes();


        //minuteView.setText(String.valueOf(stratminutes));

        // displays the variables for the different skills
        String skillName = StatsData.Skill1.getSkill();
        TextView textView = (TextView) findViewById(R.id.minutes_view);
        textView.setText(skillName+": "+stratminutes+ " minutes");

        String skillName2 = StatsData.Skill2.getSkill();
        TextView textView2 = (TextView) findViewById(R.id.text_view_id2);
        textView2.setText(skillName2+": "+logicminutes+ " minutes");

        String skillName3 = StatsData.Skill3.getSkill();
        TextView textView3 = findViewById(R.id.text_view_id3);
        textView3.setText(skillName3+": "+patternminutes+ " minutes");


    }
    public void onSettings(Bundle savedInstanceState){
        isHome=false;
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(v -> {
            editor.putString("username", "");
            editor.commit();
            onStart(savedInstanceState);
        });
    }
    private void showToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}