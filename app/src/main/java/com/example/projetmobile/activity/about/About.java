package com.example.projetmobile.activity.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.projetmobile.R;

public class About extends AppCompatActivity {

    private ImageView play1;
    private ImageView play2;
    private ImageView play3;
    private ImageView play4;
    private ImageView play5;
    private ImageView play6;
    private ImageView play7;
    private ImageView play8;
    private ImageView play9;
    private ImageView play10;
    private ImageView play11;
    private ImageView play12;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        this.play1= (ImageView) findViewById(R.id.play1);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/arnaud-yarga-876469174/"));
                startActivity(browserIntent);
            }
        });


        this.play2= (ImageView) findViewById(R.id.play2);

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ArnaudYarga"));
                startActivity(browserIntent);


            }
        });
        this.play3= (ImageView) findViewById(R.id.play3);

        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/yassine-atmani-246819187/"));
                startActivity(browserIntent);
            }
        });


        this.play4= (ImageView) findViewById(R.id.play4);

        play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yassineatm749"));
                startActivity(browserIntent);
            }
        });


        this.play5= (ImageView) findViewById(R.id.play5);

        play5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/zakarya-zaitar-a81a9b1a9/"));
                startActivity(browserIntent);
            }
        });


        this.play6= (ImageView) findViewById(R.id.play6);

        play6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zaitarzakarya"));
                startActivity(browserIntent);
            }
        });

        this.play7= (ImageView) findViewById(R.id.play7);

        play7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/chadi-laoulaou/"));
                startActivity(browserIntent);
            }
        });

        this.play8= (ImageView) findViewById(R.id.play8);

        play8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Chadiii"));
                startActivity(browserIntent);
            }
        });

        this.play9= (ImageView) findViewById(R.id.play9);

        play9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Chadiii"));
                startActivity(browserIntent);
            }
        });
        this.play10= (ImageView) findViewById(R.id.play10);

        play10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Chadiii"));
                startActivity(browserIntent);
            }
        });
        this.play11= (ImageView) findViewById(R.id.play11);

        play11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/lokman-ramdani-252428193/"));
                startActivity(browserIntent);
            }
        });
        this.play12= (ImageView) findViewById(R.id.play12);

        play12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Chadiii"));
                startActivity(browserIntent);
            }
        });









    }



}
