package com.example.projetmobile.activity.users;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Users;
import com.squareup.picasso.Picasso;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfilPictureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_picture);

        Intent i = getIntent();
        final String imageUrl = (String) i.getSerializableExtra("imageUrl");

        if(imageUrl != null){
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.profile)
                    .into((ImageView) findViewById(R.id.profile_picture_fullscreen));
        }
        else
            finish();

    }

}
