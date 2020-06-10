package com.example.projetmobile.activity.emploi;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetmobile.R;
import com.example.projetmobile.Utils.LetterImageView;
import com.example.projetmobile.model.Tabletime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetaildayconsultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private FirebaseDatabase database;
    private String a;
    private DatabaseReference rootRef;
    List<Tabletime> listemploil,listemploim,listemploime,listemploij,listemploiv,listemplois;
    ArrayList<String> subjectsl,subjectsm ,subjectsme, subjectsj,subjectsv,subjectss ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaildayconsult);
        subjectsl = new ArrayList<String>();listemploil=new ArrayList<Tabletime>();subjectsm = new ArrayList<String>();
        listemploim=new ArrayList<>();subjectsme = new ArrayList<String>();listemploime=new ArrayList<>();
        subjectsj = new ArrayList<String>();listemploij=new ArrayList<>();subjectsv = new ArrayList<String>();
        listemploiv=new ArrayList<>();subjectss = new ArrayList<String>();listemplois=new ArrayList<>();
        listemploil.clear();subjectsl.clear();listemploim.clear();subjectsm.clear();listemploime.clear();subjectsme.clear();
        listemploij.clear();subjectsj.clear();listemploiv.clear();subjectsv.clear();listemplois.clear();subjectss.clear();

        setupUIViews();
        initToolbar();
    }

    @Override
    protected void onStart() {

        if(userYearActivity.ssharedPreferences.getString(userYearActivity.SEL_YEAR, null) == null){
            a = YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR, null);
        }else {
            a = userYearActivity.ssharedPreferences.getString(userYearActivity.SEL_YEAR, null);
        }

        super.onStart();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Tabletime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> tablet = document.getData();



                            if (a.equals("2éme année") && ((String)tablet.get("année")).equals("2éme année")) {

                                switch ((String)tablet.get("jour")) {
                                    case "Lundi":
                                        subjectsl.add((String)tablet.get("matiere"));
                                        listemploil.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Mardi":
                                        subjectsm.add((String)tablet.get("matiere"));
                                        listemploim.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Mercredi":
                                        subjectsme.add((String)tablet.get("matiere"));
                                        listemploime.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Jeudi":
                                        subjectsj.add((String)tablet.get("matiere"));
                                        listemploij.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Vendredi":
                                        subjectsv.add((String)tablet.get("matiere"));
                                        listemploiv.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Samedi":
                                        subjectss.add((String)tablet.get("matiere"));
                                        listemplois.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));
                                        break;
                                }
                            }
                            if (a.equals("3éme année") && ((String)tablet.get("année")).equals("3éme année")) {
                                switch ((String)tablet.get("jour")) {
                                    case "Lundi":
                                        subjectsl.add((String)tablet.get("matiere"));
                                        listemploil.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Mardi":
                                        subjectsm.add((String)tablet.get("matiere"));
                                        listemploim.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Mercredi":
                                        subjectsme.add((String)tablet.get("matiere"));
                                        listemploime.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Jeudi":
                                        subjectsj.add((String)tablet.get("matiere"));
                                        listemploij.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Vendredi":
                                        subjectsv.add((String)tablet.get("matiere"));
                                        listemploiv.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));

                                        break;
                                    case "Samedi":
                                        subjectss.add((String)tablet.get("matiere"));
                                        listemplois.add(new Tabletime((String)tablet.get("horaire"), (String)tablet.get("matiere"),
                                                (String)tablet.get("enseignant"), (String)tablet.get("salle"),
                                                (String)tablet.get("jour"), (String)tablet.get("année"),
                                                (String) tablet.get("Temps d'enregistrement")));
                                        break;
                                }

                            }

                        }

                        switch (SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy, null)) {
                            case "Lundi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemploil, subjectsl);
                                listView.setAdapter(adapter);
                                break;
                            }
                            case "Mardi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemploim, subjectsm);
                                listView.setAdapter(adapter);
                                break;
                            }
                            case "Mercredi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemploime, subjectsme);
                                listView.setAdapter(adapter);
                                break;
                            }
                            case "Jeudi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemploij, subjectsj);
                                listView.setAdapter(adapter);
                                break;
                            }
                            case "Vendredi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemploiv, subjectsv);
                                listView.setAdapter(adapter);
                                break;
                            }
                            case "Samedi": {
                                EmploiList adapter = new EmploiList(DetaildayconsultActivity.this, listemplois, subjectss);
                                listView.setAdapter(adapter);
                                break;
                            }
                        }

                    }
                });
            }

    private void setupUIViews(){
        toolbar = (Toolbar)findViewById(R.id.Toolbardetaildayconsult);
        listView = (ListView)findViewById(R.id.lvdetaildayconsult);
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy,null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public class EmploiList extends ArrayAdapter<Tabletime> {
        private Activity context;
        private List<Tabletime> tabletimelist;
        private ArrayList<String> cours;


        public EmploiList(Activity context1, List<Tabletime> tabletimelist, ArrayList<String> subjects) {
            super(context1, R.layout.activity_dayconsult_single_item,tabletimelist);
            this.context = context1;
            this.tabletimelist = tabletimelist;
            this.cours=subjects;

        }


        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            LayoutInflater  inflater=context.getLayoutInflater();
            View listViewitem=inflater.inflate(R.layout.activity_dayconsult_single_item,null,true);
            TextView matiere=(TextView)listViewitem.findViewById(R.id.tvonsultsingleitemsubject);
            TextView enseignant=(TextView)listViewitem.findViewById(R.id.tvonsultsingleitemens);
            TextView salle=(TextView)listViewitem.findViewById(R.id.tvonsultsingleitemsalle);
            TextView horaire=(TextView)listViewitem.findViewById(R.id.tvonsultsingleitemtime);
            LetterImageView letterImageView=(LetterImageView)listViewitem.findViewById(R.id.ivdayconsultsingleitem);

            Tabletime tabletime=tabletimelist.get(position);
            matiere.setText(tabletime.getMatiere());
            enseignant.setText(tabletime.getEnseignant());
            salle.setText(tabletime.getSalle());
            horaire.setText(tabletime.getHoraire());
            letterImageView.setOval(true);
            letterImageView.setLetter(cours.get(position).charAt(0));
            return listViewitem;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
