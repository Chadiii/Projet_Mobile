package com.example.projetmobile.activity.messagerie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Message;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MessageList extends AppCompatActivity {
    private String mode; //messageSent or messageReceived
//    private ProgressDialog loadingBar;
    private ArrayList<Message> arrayOfMessages;
    private MessageAdapter adapter;
    private ListView listView;
    private LinearLayout emptyGroup;
    private LinearLayout progressBar;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Intent i = getIntent();
        mode = (String) i.getSerializableExtra("mode");

        progressBar = findViewById(R.id.message_list_progress_bar);
        emptyGroup = findViewById(R.id.empty_box_group);
        emptyText = findViewById(R.id.empty_box_text);

        FloatingActionButton fab = findViewById(R.id.fab_new_message);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessage.class);
                startActivity(intent);
            }
        });

        adapter = new MessageAdapter(this, arrayOfMessages);
        listView =  findViewById(R.id.lv_messages_list);

//        loadingBar = new ProgressDialog(this);
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();

        if (mode.compareTo("messageReceived")==0)
            searchReceivedMessages();
        else
            searchSentMessages();
    }

    public void searchReceivedMessages(){
        arrayOfMessages = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Messages")
                .whereEqualTo("destinataires.email", Users.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> dest;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> messageData = document.getData();
                                dest = (Map<String, Object>) messageData.get("destinataires");
                                arrayOfMessages.add(new Message(
                                        document.getId(),
                                        (String)messageData.get("objet"),
                                        (String)messageData.get("emetteur"),
                                        (String)messageData.get("contenu"),
                                        dest,
                                        (Boolean) dest.get("vue"),
                                        (String)messageData.get("date")));

                            }
                            if(arrayOfMessages.size()==0){
                                emptyText.setText("Aucun message reçu");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                Collections.sort(arrayOfMessages, Collections.reverseOrder());
                                adapter = new MessageAdapter(adapter.context, arrayOfMessages);
                                listView.setAdapter(adapter);
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emptyText.setText("Une erreur s'est produite");
                            emptyGroup.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void searchSentMessages(){
        arrayOfMessages = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Messages")
                .whereEqualTo("emetteur", Users.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> dest;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> messageData = document.getData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                dest = (Map<String, Object>) messageData.get("destinataires");
                                arrayOfMessages.add(new Message(
                                        document.getId(),
                                        (String)messageData.get("objet"),
                                        (String)messageData.get("emetteur"),
                                        (String)messageData.get("contenu"),
                                        dest,
                                        (Boolean) dest.get("vue"),
                                        (String)messageData.get("date")));
                            }
                            if(arrayOfMessages.size()==0) {
                                emptyText.setText("Aucun message envoyé");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                Collections.sort(arrayOfMessages, Collections.reverseOrder());
                                adapter = new MessageAdapter(adapter.context, arrayOfMessages);
                                listView.setAdapter(adapter);
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emptyText.setText("Une erreur s'est produite");
                            emptyGroup.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
