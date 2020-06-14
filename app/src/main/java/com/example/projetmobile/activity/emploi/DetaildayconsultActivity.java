package com.example.projetmobile.activity.emploi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Tabletime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class DetaildayconsultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView emploitrecyclerview;
    RecyclerView.LayoutManager layoutManager;
    EmploiAdapter emploiAdapter;
    ProgressDialog progressDialog;
    List<Tabletime> listemploil,listemploim,listemploime,listemploij,listemploiv,listemplois;
    ArrayList<String> subjectsl,subjectsm ,subjectsme, subjectsj,subjectsv,subjectss ;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaildayconsult);
        Initialisation();
        Clear();
        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        setupUIViews();
        initToolbar();
        emploitrecyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        emploitrecyclerview.setLayoutManager(layoutManager);
        showdata();

    }
    private void showdata() {
        progressDialog.setTitle("Loading Data ... ");
        progressDialog.show();
        db.collection("Tabletime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Clear();
                        progressDialog.dismiss();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                             Tabletime tabletime= new Tabletime(doc.getString("horaire"),doc.getString("matiere"),
                                    doc.getString("enseignant"),doc.getString("salle"),
                                    doc.getString("jour"),doc.getString("année"),doc.getString("id"));
                                    ChargementData(tabletime);
                        }
                        ChargementAdapter();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DetaildayconsultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public  void deleteData (int index){
       progressDialog.setTitle( "Deleting Data...");
       progressDialog.show();
        if (YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR, null).equals("2éme année")) {
            switch (SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy, null)) {
                case "Lundi": {
                    deletelist(listemploil, index);
                    showdata();
                    break;
                }
                case "Mardi": {
                    deletelist(listemploim, index);
                    showdata();
                    break;
                }
                case "Mercredi": {
                    deletelist(listemploime, index);
                    showdata();
                    break;
                }
                case "Jeudi": {
                    deletelist(listemploij, index);
                    showdata();
                    break;
                }
                case "Vendredi": {
                    deletelist(listemploiv, index);
                    showdata();
                    break;
                }
                case "Samedi": {
                    deletelist(listemplois, index);
                    showdata();
                    break;
                }
            }
        }
            if (YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR, null).equals("3éme année")){
                switch (SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy, null)) {
                    case "Lundi": {
                        deletelist(listemploil, index);
                        showdata();
                        break;
                    }
                    case "Mardi": {
                        deletelist(listemploim, index);
                        showdata();
                        break;
                    }
                    case "Mercredi": {
                        deletelist(listemploime, index);
                        showdata();
                        break;
                    }
                    case "Jeudi": {
                        deletelist(listemploij, index);
                        showdata();
                        break;
                    }
                    case "Vendredi": {
                        deletelist(listemploiv, index);
                        showdata();
                        break;
                    }
                    case "Samedi": {
                        deletelist(listemplois, index);
                        showdata();
                        break;
                    }
                }
            }
    }

    private void setupUIViews(){
        toolbar = findViewById(R.id.Toolbardetaildayconsult);
        emploitrecyclerview = findViewById(R.id.lvdetaildayconsult);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy,null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settingsemploi){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletelist ( List<Tabletime> liste, int c){
            db.collection("Tabletime").document(liste.get(c).getId())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            Toast.makeText(DetaildayconsultActivity.this, "Deleted...", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DetaildayconsultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }

     private void Clear(){
         listemploil.clear();subjectsl.clear();listemploim.clear();subjectsm.clear();listemploime.clear();subjectsme.clear();
         listemploij.clear();subjectsj.clear();listemploiv.clear();subjectsv.clear();listemplois.clear();subjectss.clear();
     }
     private void Initialisation(){
         subjectsl = new ArrayList<>();listemploil=new ArrayList<>();subjectsm = new ArrayList<>();
         listemploim=new ArrayList<>();subjectsme = new ArrayList<>();listemploime=new ArrayList<>();
         subjectsj = new ArrayList<>();listemploij=new ArrayList<>();subjectsv = new ArrayList<>();
         listemploiv=new ArrayList<>();subjectss = new ArrayList<>();listemplois=new ArrayList<>();
     }
     //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.action_searchemploi);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchdata(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchdata(String query) {
        progressDialog.setTitle("Searching");
        progressDialog.show();
        db.collection("Tabletime").whereEqualTo("search",query.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Clear();
                        progressDialog.dismiss();
                        for (QueryDocumentSnapshot docc : task.getResult()) {
                            Tabletime tab= new Tabletime(docc.getString("horaire"),docc.getString("matiere"),
                                    docc.getString("enseignant"),docc.getString("salle"),
                                    docc.getString("jour"),docc.getString("année"),docc.getString("id"));
                            ChargementData(tab);
                        }
                        ChargementAdapter();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DetaildayconsultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void ChargementData(Tabletime tabletime1){
        if (YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR, null).equals("2éme année") && (tabletime1.getAnnée()).equals("2éme année")) {
            switch (tabletime1.getJour()) {
                case "Lundi":
                    subjectsl.add(tabletime1.getMatiere());
                    listemploil.add(tabletime1);
                    break;
                case "Mardi":
                    subjectsm.add(tabletime1.getMatiere());
                    listemploim.add(tabletime1);
                    break;
                case "Mercredi":
                    subjectsme.add(tabletime1.getMatiere());
                    listemploime.add(tabletime1);
                    break;
                case "Jeudi":
                    subjectsj.add(tabletime1.getMatiere());
                    listemploij.add(tabletime1);
                    break;
                case "Vendredi":
                    subjectsv.add(tabletime1.getMatiere());
                    listemploiv.add(tabletime1);
                    break;
                case "Samedi":
                    subjectss.add(tabletime1.getMatiere());
                    listemplois.add(tabletime1);
                    break;
            }
        }
        if (YearActivity.ssharedPreferences.getString(YearActivity.SEL_YEAR, null).equals("3éme année") && (tabletime1.getAnnée()).equals("3éme année")) {
            switch (tabletime1.getJour()) {
                case "Lundi":
                    subjectsl.add(tabletime1.getMatiere());
                    listemploil.add(tabletime1);
                    break;
                case "Mardi":
                    subjectsm.add(tabletime1.getMatiere());
                    listemploim.add(tabletime1);
                    break;
                case "Mercredi":
                    subjectsme.add(tabletime1.getMatiere());
                    listemploime.add(tabletime1);
                    break;
                case "Jeudi":
                    subjectsj.add(tabletime1.getMatiere());
                    listemploij.add(tabletime1);
                    break;
                case "Vendredi":
                    subjectsv.add(tabletime1.getMatiere());
                    listemploiv.add(tabletime1);
                    break;
                case "Samedi":
                    subjectss.add(tabletime1.getMatiere());
                    listemplois.add(tabletime1);
                    break;
            }
        }
    }
    private  void ChargementAdapter(){
        switch (SecondweekActivity.sharedPreferences.getString(SecondweekActivity.dayy, null)) {
            case "Lundi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this,listemploil, subjectsl);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
            case "Mardi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this, listemploim, subjectsm);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
            case "Mercredi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this, listemploime, subjectsme);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
            case "Jeudi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this, listemploij, subjectsj);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
            case "Vendredi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this, listemploiv, subjectsv);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
            case "Samedi": {
                emploiAdapter = new EmploiAdapter(DetaildayconsultActivity.this, listemplois, subjectss);
                emploitrecyclerview.setAdapter(emploiAdapter);
                break;
            }
        }

    }
}
