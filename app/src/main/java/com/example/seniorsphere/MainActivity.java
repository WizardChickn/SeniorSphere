package com.example.seniorsphere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String name;
    EditText nameInput;
    Button submitButton;
    Bundle savedInstanceState;
    Boolean isHome = false;

    TimeTracker stratstopwatch = new TimeTracker();
    TimeTracker logicstopwatch = new TimeTracker();
    TimeTracker patternstopwatch = new TimeTracker();

    @Override
    public void onBackPressed() { //this only works with the physical tablet
        //super.onBackPressed();
        if (isHome) super.onBackPressed();
        else {
            stratstopwatch.stopStopwatch();
            logicstopwatch.stopStopwatch();
            patternstopwatch.stopStopwatch();
            toHome(savedInstanceState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        isHome=true;
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        nameInput = (EditText) findViewById(R.id.nameInput);

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                showToast(name);
                toHome(savedInstanceState);

            }
        });
    }

    protected void toHome(Bundle savedInstanceState) {
        isHome=false;
        setContentView(R.layout.homescreen);

//different buttons lead to different pages
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicstopwatch.resetStopwatch();
                logicstopwatch.startStopwatch();
                String url = "https://www.nytimes.com/games/wordle/index.html";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patternstopwatch.resetStopwatch();
                patternstopwatch.startStopwatch();
                String url = "https://www.nytimes.com/games/connections";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicstopwatch.resetStopwatch();
                logicstopwatch.startStopwatch();
                String url = "https://sudoku.com/";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stratstopwatch.resetStopwatch();
                stratstopwatch.startStopwatch();
                String url = "https://www.chess.com/";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
            }
        });

        Button moveToStats = findViewById(R.id.moveToStats);
        moveToStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStats(savedInstanceState);
            }
        });
    }

    //loads embedded websites
    //numerous functions to prevent errors with loading in websites
    //javascript enabled to handle difficulties with websites
    protected void loadWeb(Bundle savedInstanceState, int web, String url) {
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

    protected void onStats(Bundle savedInstanceState){
        isHome=false;
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);


        TextView minuteView = findViewById(R.id.minutes_view);

        int stratminutes = stratstopwatch.getMinutes();
        int logicminutes = logicstopwatch.getMinutes();
        int patternminutes = patternstopwatch.getMinutes();

        minuteView.setText(String.valueOf(stratminutes));

        // displays the variables for the different skills
        String skillName = StatsData.Skill1.getSkill();
        double skillHours = StatsData.Skill1.getHours();
        TextView textView = (TextView) findViewById(R.id.minutes_view);
        textView.setText(skillName+": "+stratminutes+ " minutes");

        String skillName2 = StatsData.Skill2.getSkill();
        double skillHours2 = StatsData.Skill2.getHours();
        TextView textView2 = (TextView) findViewById(R.id.text_view_id2);
        textView2.setText(skillName2+": "+logicminutes+ " minutes");

        String skillName3 = StatsData.Skill3.getSkill();
        double skillHours3 = StatsData.Skill3.getHours();
        TextView textView3 = (TextView) findViewById(R.id.text_view_id3);
        textView3.setText(skillName3+": "+patternminutes+ " minutes");

    }

    private void showToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }
    public String getName(){
        return name;
    }

}