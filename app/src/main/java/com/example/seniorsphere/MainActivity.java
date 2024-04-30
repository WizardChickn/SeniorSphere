package com.example.seniorsphere;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    String name = new String();
    EditText nameInput;
    Button submitButton;
    Bundle savedInstanceState;
    Boolean isHome = false;

    @Override
    public void onBackPressed() { //this only works with the physical tablet
        //super.onBackPressed();
        if (isHome) super.onBackPressed();
        else toHome(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
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


        nameInput = (EditText) findViewById(R.id.nameInput);

            submitButton = (Button) findViewById(R.id.submitButton);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = nameInput.getText().toString();
                    editor.putString("username", name);
                    editor.putFloat("time1", 0);
                    editor.putFloat("time2", 0);
                    editor.putFloat("time3", 0);
                    editor.commit();

                    toHome(savedInstanceState);

                }
            });}
        else toHome(savedInstanceState);

    }

    protected void toHome(Bundle savedInstanceState) {
        isHome=false;
        setContentView(R.layout.homescreen);

        TextView message= (TextView) findViewById(R.id.motivationalMessage);
        message.setText(makeMessage());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        showToast("Welcome "+sharedPreferences.getString("username", "defaultUsername"));
//different buttons lead to different pages
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.nytimes.com/games/wordle/index.html";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
               //onWordle(savedInstanceState);
                //
              //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
             //   startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.nytimes.com/games/connections";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
                //
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
               // startActivity(intent);

            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://sudoku.com/";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
               // startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.chess.com/";
                loadWeb(savedInstanceState, R.layout.web_loader, url);
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                //startActivity(intent);
            }
        });

        Button moveToStats = findViewById(R.id.moveToStats);
        moveToStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStats(savedInstanceState);
            }
        });

        Button moveToSettings = findViewById(R.id.moveToSettings);
        moveToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettings(savedInstanceState);
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

    public String makeMessage() {
        //make an arraylist of inpirational and positive messages
        ArrayList<String> messages = new ArrayList<String>();
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

    protected void onStats(Bundle savedInstanceState){
        isHome=false;
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        // displays the variables for the different skills
        String skillName = StatsData.Skill1.getSkill();
        double skillHours = StatsData.Skill1.getHours();
        TextView textView = (TextView) findViewById(R.id.text_view_id);
        textView.setText(skillName+": "+skillHours+" hours");

        String skillName2 = StatsData.Skill2.getSkill();
        double skillHours2 = StatsData.Skill2.getHours();
       TextView textView2 = (TextView) findViewById(R.id.text_view_id2);
        textView2.setText(skillName2+": "+skillHours2+" hours");

        String skillName3 = StatsData.Skill3.getSkill();
        double skillHours3 = StatsData.Skill3.getHours();
        TextView textView3 = (TextView) findViewById(R.id.text_view_id3);
        textView3.setText(skillName3+": "+skillHours3+" hours");

    }



    public void onSettings(Bundle savedInstanceState){
        isHome=false;
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("username", "");
                editor.commit();
                onStart(savedInstanceState);
            }
        });
    }



    private void showToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }
    public String getName(){
        return name;
    }


}
