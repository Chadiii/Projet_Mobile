package com.example.projetmobile.activity.emploi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetmobile.R;

public class ConsultCreateActivity extends AppCompatActivity {

        private Toolbar toolbar;
        private Button button1;
        private Button button2;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consult_create);
            setupUIViews();
            initToolbar();
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.projetmobile.activity.emploi.ConsultCreateActivity.this, SecondweekActivity.class);
                    startActivity(intent);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.projetmobile.activity.emploi.ConsultCreateActivity.this, WeekActivity.class);
                    startActivity(intent);
                }
            });

        }

        private void setupUIViews() {
            toolbar = (Toolbar) findViewById(R.id.ToolbarConsultCreate);
            button1 = (Button) findViewById(R.id.Consult_btn);
            button2 = (Button) findViewById(R.id.Create_btn);
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


