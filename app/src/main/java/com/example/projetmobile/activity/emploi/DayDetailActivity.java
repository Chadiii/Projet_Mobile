package com.example.projetmobile.activity.emploi;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.projetmobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DayDetailActivity extends AppCompatActivity {
    private Button EnregistreButton;
    private EditText Inputmatiere, Inputenseignant, Inputsalle, Inputhouer;
    private Toolbar toolbar;
    public static SimpleDateFormat formater = null;
    Date aujourdhui = new Date();
    String uid,umatiere,usalle,uenseignet,uhoraire;
    FirebaseFirestore db;

    public DayDetailActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        formater = new SimpleDateFormat("yyyyMMddHHmmss");
        setupUIViews();
        initToolbar();

        db = FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            //update data
            getSupportActionBar().setTitle("La Mise à jour d'une scéance");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            EnregistreButton.setText("Mettre à jour");
            //get data
            uid = bundle.getString("uid");
            umatiere = bundle.getString("umatiere");
            uenseignet = bundle.getString("uenseignet");
            usalle = bundle.getString("usalle");
            uhoraire = bundle.getString("uhoraire");
            //set data
            Inputmatiere.setText(umatiere);
            Inputenseignant.setText(uenseignet);
            Inputhouer.setText(uhoraire);
            Inputsalle.setText(usalle);

        }
        else{ getSupportActionBar().setTitle("L'ajout d'une scéance");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            EnregistreButton.setText("Enregistrer");

        }

        EnregistreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if(bundle1   != null){
                    String id = uid;
                    String heure = Inputhouer.getText().toString().trim();
                    String matiere = Inputmatiere.getText().toString().trim();
                    String enseignant = Inputenseignant.getText().toString().trim();
                    String salle = Inputsalle.getText().toString().trim();
                    updatedate(id, heure, matiere, enseignant, salle);
                    Toast.makeText(getApplicationContext(), "La scénace a bien été mise à jour", Toast.LENGTH_LONG).show();
                    finish();

                }
                else {

                    //input data
                    String heure = Inputhouer.getText().toString().trim();
                    String matiere = Inputmatiere.getText().toString().trim();
                    String enseignant = Inputenseignant.getText().toString().trim();
                    String salle = Inputsalle.getText().toString().trim();

                    if (TextUtils.isEmpty(heure)) {
                        Toast.makeText(DayDetailActivity.this, "Please remplissez l'heure ...", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(matiere)) {
                        Toast.makeText(DayDetailActivity.this, "Please remplissez la matiére ...", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(enseignant)) {
                        Toast.makeText(DayDetailActivity.this, "Please remplissez l'enseignent...", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(salle)) {
                        Toast.makeText(DayDetailActivity.this, "Please remplissez la salle ...", Toast.LENGTH_SHORT).show();
                    }
                    //function call to upload data
                    else {
                        AddTableTime(formater.format(aujourdhui), YearActivity.ssharedPreferences.
                                getString(YearActivity.SEL_YEAR, null), WeekActivity.sharedPreferences.
                                getString(WeekActivity.SEL_DAY, null), heure, matiere, enseignant, salle);
                        Toast.makeText(getApplicationContext(), "La scéance a bien été crée", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            private void updatedate(String id,String heure, String matiere, String enseignant, String salle) {

                db.collection("Tabletime").document(id)
                        .update("horaire",heure,"matiere",matiere,"enseignant",enseignant,"salle",salle,"search",matiere.toLowerCase())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("mise à jour", "La scéance successfully mise à jour!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Non mise à jour", "Error writing scéance",e);
                            }
                        });
            }
        });
    }
    private void setupUIViews(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarDay);
        EnregistreButton = (Button) findViewById(R.id.DayEnregister_btn);
        Inputhouer = (EditText) findViewById(R.id.Houers_input);
        Inputmatiere = (EditText) findViewById(R.id.course_input);
        Inputenseignant = (EditText) findViewById(R.id.enseignant_input);
        Inputsalle = (EditText) findViewById(R.id.salle_input);
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ajout d'une scéance");
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
    private void AddTableTime(final String formater,final String année, final String jour, final String heure,
                              final String matiere, final String enseignant, final String salle) {


        HashMap<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("id", formater);
        userDataMap.put("année", année);
        userDataMap.put("jour", jour);
        userDataMap.put("horaire", heure);
        userDataMap.put("matiere", matiere);
        userDataMap.put("enseignant", enseignant);
        userDataMap.put("salle", salle);
        userDataMap.put("search",matiere.toLowerCase());

        //  Adding data to Firestore
        db.collection("Tabletime").document(formater)
                .set(userDataMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Enregistré", "La scéance successfully enregistrée!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Non Enregistré", "Error writing scéance",e);
                    }
                });
    }
}




