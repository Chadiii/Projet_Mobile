package com.example.projetmobile.activity.PlanDeFormation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Module;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateModuleActivity extends AppCompatActivity implements View.OnClickListener {



    private EditText editTextSemestre;
    private EditText editTextCode;
    private EditText editTextIntitule;
    private EditText editTextVolume;
    private Toolbar toolbar;


    private FirebaseFirestore db;

    private Module module;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);

        setupUIViews();
        initToolbar();

        module = (Module) getIntent().getSerializableExtra("module");
        db = FirebaseFirestore.getInstance();

        editTextSemestre = findViewById(R.id.edittext_semestre);
        editTextCode = findViewById(R.id.edittext_code);
        editTextIntitule = findViewById(R.id.edittext_intitule);
        editTextVolume = findViewById(R.id.edittext_volume);

        editTextSemestre.setText(module.getSemestre());
        editTextCode.setText(module.getCode());
        editTextIntitule.setText(module.getIntitule());
        editTextVolume.setText(String.valueOf(module.getVolume()));


        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String semstre, String code, String intitule, String volume) {
        if (semstre.isEmpty()) {
            editTextSemestre.setError("Semestre requis");
            editTextSemestre.requestFocus();
            return true;
        }

        if (code.isEmpty()) {
            editTextCode.setError("Code requis");
            editTextCode.requestFocus();
            return true;
        }

        if (intitule.isEmpty()) {
            editTextIntitule.setError("Intitule requis");
            editTextIntitule.requestFocus();
            return true;
        }



        if (volume.isEmpty()) {
            editTextVolume.setError("Volume requis");
            editTextVolume.requestFocus();
            return true;
        }
        return false;
    }


    private void updateModule() {
        String semestre = editTextSemestre.getText().toString().trim();
        String code = editTextCode.getText().toString().trim();
        String intitule = editTextIntitule.getText().toString().trim();
        String volume = editTextVolume.getText().toString().trim();

        if (!hasValidationErrors(semestre, code, intitule, volume)) {

            Module m = new Module(
                    semestre, code, intitule,

                    Integer.parseInt(volume)
            );


            db.collection("Plan de Formation").document(module.getId())
                    .update(
                            "code", m.getCode(),
                            "intitule", m.getIntitule(),
                            "semestre", m.getSemestre(),
                            "volume", m.getVolume()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateModuleActivity.this, "Module mis à jour", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void deleteProduct() {
        db.collection("Plan de Formation").document(module.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateModuleActivity.this, "Module supprimé", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(UpdateModuleActivity.this, ModulesActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                updateModule();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Vous êtes sûr de vouloir supprimer ce module de la formation IWIM?");
                builder.setMessage("La suppression est irreversible...");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }

    private void setupUIViews() {
        toolbar = (Toolbar) findViewById(R.id.Toolbarupdate);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan de formation I.W.I.M");
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
