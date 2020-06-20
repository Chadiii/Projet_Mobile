package com.example.projetmobile.activity.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetmobile.MainActivity;
import com.example.projetmobile.R;
import com.example.projetmobile.model.IntroAdapter;
import com.example.projetmobile.model.IntroContent;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager intro_viewpager;
    private Button intro_start_btn;
    private TextView intro_next_textview;
    int position;
    private IntroAdapter introAdapter;
    Animation into_start_btn_anim;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        intro_viewpager = findViewById(R.id.intro_viewpager_id);
        intro_next_textview = findViewById(R.id.intro_next_textview_id);
        intro_start_btn = findViewById(R.id.intro_start_btn_id);
        into_start_btn_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        final List<IntroContent> intro_content_list = new ArrayList<>();
        String intro_details = getResources().getString(R.string.intro_details);
        String intro_contacts = getResources().getString(R.string.intro_contacts);
        String intro_blood_donation1 = getResources().getString(R.string.intro_blood_donation1);
        String intro_blood_donation2 = getResources().getString(R.string.intro_blood_donation2);
        String intro_blood_donation3 = getResources().getString(R.string.intro_blood_donation3);
        String intro_blood_donation4 = getResources().getString(R.string.intro_blood_donation4);
        String intro_blood_donation5 = getResources().getString(R.string.intro_blood_donation5);
        String intro_blood_donation6 = getResources().getString(R.string.intro_blood_donation6);

        String intro_find_location = getResources().getString(R.string.intro_find_location);

        intro_content_list.add(new IntroContent("Bienvenue sur Application Mobile dédié à la filière IWIM", "Réalisé par:", R.drawable.applogo));
        intro_content_list.add(new IntroContent(intro_blood_donation2,intro_details, R.drawable.arnaud));
        intro_content_list.add(new IntroContent(intro_blood_donation1,intro_details, R.drawable.yassinee));
        intro_content_list.add(new IntroContent(intro_blood_donation3,intro_details, R.drawable.zakarya));
        intro_content_list.add(new IntroContent(intro_blood_donation4,intro_details, R.drawable.chaadi));
        intro_content_list.add(new IntroContent(intro_blood_donation5,intro_details, R.drawable.git));
        intro_content_list.add(new IntroContent(intro_blood_donation6,intro_details, R.drawable.lokmane));



        intro_content_list.add((new IntroContent("\uD83D\uDCBBENSIAS-IWIM\uD83D\uDCBB", "BIENVENUE", R.drawable.applogo)));


        introAdapter = new IntroAdapter(this, intro_content_list);
        intro_viewpager.setAdapter(introAdapter);

        intro_next_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = intro_viewpager.getCurrentItem();
                if (position < intro_content_list.size()) {
                    position++;
                    intro_viewpager.setCurrentItem(position);
                }
                if (position == intro_content_list.size() - 1) {
                    intro_load_last_Screen();
                }
            }
        });
        intro_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }


    private void intro_load_last_Screen() {
        intro_next_textview.setVisibility(View.INVISIBLE);
        intro_start_btn.setVisibility(View.VISIBLE);
        intro_start_btn.setAnimation(into_start_btn_anim);
    }
}
