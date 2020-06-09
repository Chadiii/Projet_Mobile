package com.example.projetmobile.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.activity.PlanDeFormation.MainActivity1;
import com.example.projetmobile.activity.chefdefiliere.GestionProfesseurActivity;

public class ChefFiliereActivity extends AppCompatActivity {

    private Button GestionProfesseur, Deconnexion, AjoutEmplBtn, AjoutePlanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_chef_filiere);

       GestionProfesseur = findViewById(R.id.GestionProfbutton);
       Deconnexion = findViewById(R.id.Deconnexion);
       AjoutEmplBtn = findViewById(R.id.AjoutEmploi);
       AjoutePlanBtn = findViewById(R.id.AjoutPlanForm);

       GestionProfesseur.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent( ChefFiliereActivity.this, GestionProfesseurActivity.class);
               startActivity(intent);
           }
       });

       Deconnexion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent( ChefFiliereActivity.this, LoginActivity.class);
               startActivity(intent);
           }
       });

       AjoutePlanBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent( ChefFiliereActivity.this, MainActivity1.class);
               startActivity(intent);
           }
       });

       AjoutEmplBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent( ChefFiliereActivity.this, GestionProfesseurActivity.class);
               startActivity(intent);
           }
       });


    }
}
