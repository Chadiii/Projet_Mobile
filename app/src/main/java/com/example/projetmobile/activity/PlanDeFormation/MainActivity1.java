package com.example.projetmobile.activity.PlanDeFormation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Module;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {



    private EditText editTextSemestre;
    private EditText editTextCode;
    private EditText editTextIntitule;
    private EditText editTextVolume;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        db = FirebaseFirestore.getInstance();

        editTextSemestre = findViewById(R.id.edittext_semestre);
        editTextCode = findViewById(R.id.edittext_code);
        editTextIntitule = findViewById(R.id.edittext_intitule);
        editTextVolume = findViewById(R.id.edittext_volume);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_modules).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String semestre, String code, String intitule, String volume) {
        if (semestre.isEmpty()) {
            editTextSemestre.setError("Semestre required");
            editTextSemestre.requestFocus();
            return true;
        }

        if (code.isEmpty()) {
            editTextCode.setError("Code required");
            editTextCode.requestFocus();
            return true;
        }

        if (intitule.isEmpty()) {
            editTextIntitule.setError("Intitulé required");
            editTextIntitule.requestFocus();
            return true;
        }



        if (volume.isEmpty()) {
            editTextVolume.setError("Volume required");
            editTextVolume.requestFocus();
            return true;
        }
        return false;
    }


    private void saveModule(){
        String semestre = editTextSemestre.getText().toString().trim();
        String code = editTextCode.getText().toString().trim();
        String intitule = editTextIntitule.getText().toString().trim();
        String volume = editTextVolume.getText().toString().trim();

        if (!hasValidationErrors(semestre, code, intitule, volume)) {

            CollectionReference dbProducts = db.collection("Plan de Formation");

            Module module = new Module(
                    semestre,
                    code,
                    intitule,
                    Integer.parseInt(volume)
            );

            dbProducts.add(module)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity1.this, " Module ajouté", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity1.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.button_save:
                saveModule();
                break;
            case R.id.textview_view_modules:
                startActivity(new Intent(this, ModulesActivity.class));
                break;
        }

    }
}
