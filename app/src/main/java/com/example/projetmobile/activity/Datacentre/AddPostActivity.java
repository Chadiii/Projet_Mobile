package com.example.projetmobile.activity.Datacentre;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity {

    private EditText title,description,author;
    private TextView text;
    private Button save,read;
    private DatabaseReference Post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              save();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // readOneTime();
                readRealTime();
            }
        });


    }

    private void readRealTime() {
        Post.child("-Ln9F3Km8HlwqS0oaD9Q")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String post = "url : "+dataSnapshot.child("url").getValue(String.class)+"\n"
                                +"name : "+dataSnapshot.child("name").getValue(String.class);

                        text.setText(post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void readOneTime() {
         Post.child("-Ln9F3Km8HlwqS0oaD9Q")
                 .addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         String post = "url : "+dataSnapshot.child("url").getValue(String.class)+"\n"
                                 +"name : "+dataSnapshot.child("name").getValue(String.class);

                         text.setText(post);

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
    }

    private void save() {
        HashMap<String,Object> map = new HashMap<>();

        map.put("url",description.getText().toString());
        map.put("name",author.getText().toString());

        Post.push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("jfbvkj", "onComplete: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("jfbvkj", "onFailure: "+e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("jfbvkj", "onSuccess: ");
            }
        });
    }

    private void init() {
        author = findViewById(R.id.author);

        text = findViewById(R.id.text);

        Post = FirebaseDatabase.getInstance().getReference().child("Datacentre");
    }


}
