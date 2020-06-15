package com.example.projetmobile.activity.emploi;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.projetmobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DayDetailActivity extends AppCompatActivity {
    private Button EnregistreButton;
    private TextView Inputhouer1,Inputhouer2;
    private EditText Inputmatiere, Inputenseignant, Inputsalle;
    private Toolbar toolbar;
    public static SimpleDateFormat formater = null;
    Date aujourdhui = new Date();
    String uid,umatiere,usalle,uenseignet,uheuredebut,uheurefin;
    FirebaseFirestore db;
    int t1Houer, t1Minute,t2Houer,t2Minute;

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
            uheuredebut= bundle.getString("heuredebut");
            uheurefin= bundle.getString("heurefin");
            //set data
            Inputmatiere.setText(umatiere);
            Inputenseignant.setText(uenseignet);
            Inputhouer1.setText(uheuredebut);
            Inputhouer2.setText(uheurefin);
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
                    String heuredebut = Inputhouer1.getText().toString().trim();
                    String heurefin = Inputhouer2.getText().toString().trim();
                    String matiere = Inputmatiere.getText().toString().trim();
                    String enseignant = Inputenseignant.getText().toString().trim();
                    String salle = Inputsalle.getText().toString().trim();
                    updatedate(id, heuredebut,heurefin, matiere, enseignant, salle);
                    Toast.makeText(getApplicationContext(), "La scénace a bien été mise à jour", Toast.LENGTH_LONG).show();
                    finish();

                }
                else {

                    //input data
                    String heuredebut = Inputhouer1.getText().toString().trim();
                    String heurefin = Inputhouer2.getText().toString().trim();
                    String matiere = Inputmatiere.getText().toString().trim();
                    String enseignant = Inputenseignant.getText().toString().trim();
                    String salle = Inputsalle.getText().toString().trim();

                    if (TextUtils.isEmpty(heuredebut)) {
                        Toast.makeText(DayDetailActivity.this, "Please remplissez l'heure ...", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(heurefin)) {
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
                                getString(WeekActivity.SEL_DAY, null), heuredebut,heurefin, matiere, enseignant, salle);
                        Toast.makeText(getApplicationContext(), "La scéance a bien été crée", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            private void updatedate(String id,String heure1,String heure2, String matiere, String enseignant, String salle) {

                db.collection("Tabletime").document(id)
                        .update("heuredebut",heure1,"heurefin",heure2,"matiere",matiere,"enseignant",enseignant,"salle",salle,"search",matiere.toLowerCase())
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
        toolbar = findViewById(R.id.ToolbarDay);
        EnregistreButton =  findViewById(R.id.DayEnregister_btn);
        Inputhouer1 =  findViewById(R.id.tv_timer);
        Inputhouer2 =  findViewById(R.id.tv_timer1);
        Inputmatiere =  findViewById(R.id.course_input);
        Inputenseignant =  findViewById(R.id.enseignant_input);
        Inputsalle =  findViewById(R.id.salle_input);
        Inputhouer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DayDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Houer = hourOfDay;
                                t1Minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,t1Houer,t1Minute);
                                Inputhouer1.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Houer,t1Minute);
                timePickerDialog.show();
            }
        });
        Inputhouer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DayDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Houer = hourOfDay;
                                t2Minute = minute;
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(0,0,0,t2Houer,t2Minute);
                                Inputhouer2.setText(DateFormat.format("hh:mm aa",calendar1));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t2Houer,t2Minute);
                timePickerDialog.show();
            }
        });
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
    private void AddTableTime(final String formater,final String année, final String jour, final String heuredebut,String heurefin,
                              final String matiere, final String enseignant, final String salle) {


        HashMap<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("id", formater);
        userDataMap.put("année", année);
        userDataMap.put("jour", jour);
        userDataMap.put("heuredebut", heuredebut);
        userDataMap.put("heurefin", heurefin);
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




