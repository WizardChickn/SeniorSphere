package com.example.seniorsphere;


import android.annotation.SuppressLint;
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

    //separate stopwatches for different skills
    TimeTracker stratstopwatch = new TimeTracker();
    TimeTracker logicstopwatch = new TimeTracker();
    TimeTracker patternstopwatch = new TimeTracker();

    Boolean isHome = false;


    @Override
    /*
    *actions completed when the backpress button is clicked
    *stops stopwatches for games that are running and records the time spent
    */
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isHome) super.onBackPressed();

        else {

            if (stratstopwatch.getRunning()){
                stratstopwatch.stopStopwatch();
                editor.putFloat("time1", sharedPreferences.getFloat("time1", 0) + stratstopwatch.getTime());
            }
            if (logicstopwatch.getRunning()){
                logicstopwatch.stopStopwatch();
                editor.putFloat("time2", sharedPreferences.getFloat("time2", 0)+ logicstopwatch.getTime());
            }
            if (patternstopwatch.getRunning()){
                patternstopwatch.stopStopwatch();
                editor.putFloat("time3", sharedPreferences.getFloat("time3", 0)+ patternstopwatch.getTime());
            }


            toHome(savedInstanceState);
            editor.apply();
        }
    }

    /*
    *creates the user interface when the app is loaded
    */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        isHome=true;
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);
        onStart(savedInstanceState);
    }

    /*
    *screen shown when user first enters app
    *asks user for their name
    *occurs once
    */
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
                editor.apply();

                toHome(savedInstanceState);

            });}
        else toHome(savedInstanceState);

    }

    /*
    *actions completed when going to home screen from welcome screen
    *sets a new message for the user
    *displays username
    */
    protected void toHome(Bundle savedInstanceState) {
        isHome=false;
        setContentView(R.layout.homescreen);

        TextView message= findViewById(R.id.motivationalMessage);
        message.setText(makeMessage());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        showToast("Welcome "+sharedPreferences.getString("username", "defaultUsername"));

    /*
    *creates different buttons that lead to different websites
    *resets and starts the stopwatch specific to the game skill when button is pressed
    */
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {

            logicstopwatch.startStopwatch();
            String url = "https://www.nytimes.com/games/wordle/index.html";
            loadWeb(R.layout.web_loader, url);
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {

            patternstopwatch.startStopwatch();
            String url = "https://www.nytimes.com/games/connections";
            loadWeb(R.layout.web_loader, url);
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            logicstopwatch.startStopwatch();
            String url = "https://sudoku.com/";
            loadWeb(R.layout.web_loader, url);
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> {
            stratstopwatch.startStopwatch();
            String url = "https://www.chess.com/";
            loadWeb(R.layout.web_loader, url);
        });

        Button moveToStats = findViewById(R.id.moveToStats);
        moveToStats.setOnClickListener(v -> onStats());

        Button moveToSettings = findViewById(R.id.moveToSettings);
        moveToSettings.setOnClickListener(v -> onSettings(savedInstanceState));
    }

    /*
    *loads embedded websites
    *numerous functions to prevent errors with loading in websites
    *javascript enabled to handle other difficulties with websites
    */
    protected void loadWeb(int web, String url) {
        isHome=false;
        setContentView(web);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
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
            /*
            *handles errors with opening the url
            */
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            /*
            *handles errors with closing the url
            */
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            /*
            *overrides any errors that would come up with implementing a website into the embed frame
            */
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            /*
            *overrides if a url is not working
            */
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

    /*
    *creates an arraylist of premade messages to display for the user
    *random class implemented so that a different message appears every time
    */
    public String makeMessage() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Believe you can and you're halfway there.");
        messages.add("The only way to do great work is to love what you do.");
        messages.add("Success is not final, failure is not fatal: It is the courage to continue that counts.");
        messages.add("The future belongs to those who believe in the beauty of their dreams.");
        messages.add("Your limitation—it's only your imagination.");
        messages.add("The best time to plant a tree was 20 years ago. The second best time is now.");
        messages.add("Quit, don't quit... noodles, don't noodles");
        messages.add("Every accomplishment starts with the decision to try.");
        messages.add("Yesterday is history, tomorrow is a mystery, today is a gift.");

        Random hehe = new Random();
        //generate random number between 0 and arraylist.length - 1
        int index = hehe.nextInt(9);

        return messages.get(index);
    }
    /*
     *displays stats page with different skills and how much time is spent on them
     */
    @SuppressLint("SetTextI18n")
    protected void onStats(){
        isHome=false;

        setContentView(R.layout.stats);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        float strat =  sharedPreferences.getFloat("time1", 0);
        float logic =  sharedPreferences.getFloat("time2", 0);
        float pattern = sharedPreferences.getFloat("time3", 0);

        // displays the variables for the different skills
        String skillName = StatsData.Skill1.getSkill();
        TextView textView = findViewById(R.id.minutes_view);
        textView.setText(skillName+": "+(strat/60)+ " minutes");

        String skillName2 = StatsData.Skill2.getSkill();
        TextView textView2 = findViewById(R.id.text_view_id2);
        textView2.setText(skillName2+": "+(logic/60)+ " minutes");

        String skillName3 = StatsData.Skill3.getSkill();
        TextView textView3 = findViewById(R.id.text_view_id3);
        textView3.setText(skillName3+": "+(pattern/60)+ " minutes");
    }

    /*
    *goes to the settings page
    *user data can be reset
    */
    public void onSettings(Bundle savedInstanceState){
        isHome=false;
        setContentView(R.layout.settings);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(v -> {
            editor.putString("username", "");
            editor.apply();
            onStart(savedInstanceState);
        });
    }

    /*
    *shows popup message
    */
    private void showToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}
