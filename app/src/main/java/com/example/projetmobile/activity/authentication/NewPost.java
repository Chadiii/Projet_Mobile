package com.example.projetmobile.activity.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewPost extends AppCompatActivity {
    private TextView newPostContent;
    private CheckBox newPostToTeacher;
    private CheckBox newPostToStudent1;
    private CheckBox newPostToStudent2;
    private CheckBox newPostToStudent3;
    private Button newPostButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        newPostContent = findViewById(R.id.new_post_content);
        newPostToTeacher = findViewById(R.id.new_post_toTeacher);
        newPostToStudent1 = findViewById(R.id.new_post_toStudent1);
        newPostToStudent2 = findViewById(R.id.new_post_toStudent2);
        newPostToStudent3 = findViewById(R.id.new_post_toStudent3);
        newPostButton = findViewById(R.id.new_post_button);

        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = newPostContent.getText().toString();
                Boolean toTeacher = newPostToTeacher.isChecked();
                Boolean toStudent1 = newPostToStudent1.isChecked();
                Boolean toStudent2 = newPostToStudent2.isChecked();
                Boolean toStudent3 = newPostToStudent3.isChecked();

                if(content.isEmpty()){
                    Snackbar.make(view, "Le post est vide", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(!toTeacher && !toStudent1 && !toStudent2 && !toStudent3){
                    Snackbar.make(view, "La visibilité n'est pas défini", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    /*Snackbar.make(view, "Le message a bien été envoyé", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    sendPost(content, toTeacher, toStudent1, toStudent2, toStudent3);

                    Toast.makeText(getApplicationContext(),"Le post a bien été envoyé",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    public boolean sendPost(String content, Boolean toTeacher, Boolean toStudent1, Boolean toStudent2, Boolean toStudent3){
        boolean res = true;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();


        Map<String, Object> postData = new HashMap<>();
        postData.put("content", content);
        postData.put("userMail",  Users.getCurrentUser().getEmail());
        postData.put("user",  Users.getCurrentUser().displayName());
        postData.put("date", df.format(dateobj));
        postData.put("toTeacher", toTeacher);
        postData.put("toStudent1", toStudent1);
        postData.put("toStudent2", toStudent2);
        postData.put("toStudent3", toStudent3);

        db.collection("Posts")
                .add(postData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("sent", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("not sent", "Error adding document", e);
                    }
                });

        return res;
    }
}
