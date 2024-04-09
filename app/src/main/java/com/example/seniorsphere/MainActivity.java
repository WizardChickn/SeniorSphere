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
    @Override
    protected void onCreate(Bundle savedInstanceState){
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

        setContentView(R.layout.homescreen);

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
    }
    protected void loadWeb(Bundle savedInstanceState, int web, String url) {

        setContentView(web);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if Wordle requires it
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
                handler.proceed(); // Proceed with loading the page despite SSL errors
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
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);



        String displayThis = StatsData.Skill1.getSkill();
        TextView textView = (TextView) findViewById(R.id.text_view_id);
        textView.setText(displayThis);

        String displayThis2 = StatsData.Skill2.getSkill();
        TextView textView2 = (TextView) findViewById(R.id.text_view_id2);
        textView2.setText(displayThis2);


    }


    private void showToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }
    public String getName(){
        return name;
    }
}