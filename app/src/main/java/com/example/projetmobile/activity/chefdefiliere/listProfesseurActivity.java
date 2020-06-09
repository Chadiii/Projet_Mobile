package com.example.projetmobile.activity.chefdefiliere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetmobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class listProfesseurActivity extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    // firestore instance

    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_professeur);

        //init firestore
        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recycle_view_professeur);
        mAddBtn = findViewById(R.id.addBtn);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        // show data
        showData();
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listProfesseurActivity.this, GestionProfesseurActivity.class));
                finish();
            }
        });

    }

    private void showData() {
        // set title of progress dialog
        pd.setTitle("Loading Data...");
        pd.show();

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // called when data is retrived
                        pd.dismiss();
                        // show data
                        for(DocumentSnapshot doc: task.getResult()){
                            Model model = new Model(
                                    doc.getString("nom"),
                                    doc.getString("prenom"),
                                    doc.getString("email"),
                                    doc.getString("telephone"));

                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(listProfesseurActivity.this, modelList);
                        // set adapter to recyclerView
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retireving
                        pd.dismiss();

                        Toast.makeText(listProfesseurActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        ;
    }
}
