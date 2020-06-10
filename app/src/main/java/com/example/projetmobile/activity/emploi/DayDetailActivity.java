package com.example.projetmobile.activity.emploi;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DayDetailActivity extends AppCompatActivity {
    private Button EnregistreButton;
    private EditText Inputmatiere, Inputenseignant, Inputsalle, Inputhouer;
    private Toolbar toolbar;
    private ProgressDialog loadingBar;
    public static SimpleDateFormat formater = null;
    Date aujourdhui = new Date();

    public DayDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        formater = new SimpleDateFormat("yyyyMMddHHmmss");
        setupUIViews();
        initToolbar();
        EnregistreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String heure = Inputhouer.getText().toString();
                String matiere = Inputmatiere.getText().toString();
                String enseignant = Inputenseignant.getText().toString();
                String salle = Inputsalle.getText().toString();

                if(TextUtils.isEmpty(heure)){
                    Toast.makeText(DayDetailActivity.this, "Please remplissez l'heure ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(matiere)){
                    Toast.makeText(DayDetailActivity.this, "Please remplissez la matiére ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(enseignant)){
                    Toast.makeText(DayDetailActivity.this, "Please remplissez l'enseignent...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(salle)){
                    Toast.makeText(DayDetailActivity.this, "Please remplissez la salle ...", Toast.LENGTH_SHORT).show();
                }


                else{

                    if (YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR,null).equals("2éme année")) {
                        if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Lundi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Lundi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Mardi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Mardi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Mercredi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Mercredi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Jeudi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Jeudi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Vendredi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Vendredi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Samedi")) {
                            AddTableTime(formater.format(aujourdhui),"2éme année", "Samedi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    else {
                        if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Lundi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Lundi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Mardi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Mardi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Mercredi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Mercredi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Jeudi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Jeudi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Vendredi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Vendredi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        } else if (WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY, null).equals("Samedi")) {
                            AddTableTime(formater.format(aujourdhui),"3éme année", "Samedi", heure, matiere, enseignant, salle);
                            Toast.makeText(getApplicationContext(),"Le scénace a bien été crée",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                }
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
        loadingBar = new ProgressDialog(this);
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(WeekActivity.sharedPreferences.getString(WeekActivity.SEL_DAY,null));
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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        HashMap<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("Temps d'enregistrement", formater);
        userDataMap.put("année", année);
        userDataMap.put("jour", jour);
        userDataMap.put("horaire", heure);
        userDataMap.put("matiere", matiere);
        userDataMap.put("enseignant", enseignant);
        userDataMap.put("salle", salle);

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
                        Log.w("Non Enregistré", "Error writing scénace",e);
                    }
                });
    }
}




