package com.example.seniorsphere;

import android.os.Bundle;
import android.view.View;
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


    protected void toHome(Bundle savedInstanceState){
        setContentView(R.layout.homescreen);
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