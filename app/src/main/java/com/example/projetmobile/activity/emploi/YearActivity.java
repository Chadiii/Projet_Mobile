package com.example.projetmobile.activity.emploi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetmobile.R;


public class YearActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button1;
    private Button button2;
    public static SharedPreferences ssharedPreferences;
    public static String SEL_YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        setupUIViews();
        initToolbar();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ssharedPreferences.edit().putString(SEL_YEAR, "2éme année").apply();
                Intent intent = new Intent(YearActivity.this, ConsultCreateActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ssharedPreferences.edit().putString(SEL_YEAR, "3éme année").apply();
                Intent intent = new Intent(YearActivity.this, ConsultCreateActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupUIViews() {
        toolbar = (Toolbar) findViewById(R.id.ToolbarYear);
        button1 = (Button) findViewById(R.id.Second_Year_btn);
        button2 = (Button) findViewById(R.id.Third_Year_btn);
        ssharedPreferences = getSharedPreferences("MY_YEAR", MODE_PRIVATE);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emploi du Temps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
