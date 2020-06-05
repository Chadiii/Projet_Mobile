package com.example.projetmobile.activity.pedagogie;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.projetmobile.R;

public class Absence extends AppCompatActivity {

    private CardView play1;
    private CardView play2;
    private CardView play3;
    private CardView play4;
    private CardView play5;
    private CardView play6;
    private CardView play7;
    private CardView play8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);


        this.play1= (CardView) findViewById(R.id.play1);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1l9lcu5r-Bs3DmhJajmvN9_d0p5BW5TWcn5LQmYFmRxE/edit#gid=0"));
                startActivity(browserIntent);
            }
        });


        this.play2= (CardView) findViewById(R.id.play2);

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1l9lcu5r-Bs3DmhJajmvN9_d0p5BW5TWcn5LQmYFmRxE/edit#gid=0"));
                startActivity(browserIntent);


            }
        });
        this.play3= (CardView) findViewById(R.id.play3);

        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });


        this.play4= (CardView) findViewById(R.id.play4);

        play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });


        this.play5= (CardView) findViewById(R.id.play5);

        play5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });


        this.play6= (CardView) findViewById(R.id.play6);

        play6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });


        this.play7= (CardView) findViewById(R.id.play7);

        play7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });


        this.play8= (CardView) findViewById(R.id.play8);

        play8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1sSQGVycal_TCaHIvOFPyAo29t_KUVmPtYm-LWPOamHc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });








    }



}

