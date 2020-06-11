package com.example.projetmobile.activity.messagerie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Message;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewMessage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        Intent i = getIntent();
        final Message message = (Message)i.getSerializableExtra("message");

        // ce n'et pas un nouveau message mais une reponse à un message recu
        if(message != null) {
            EditText receptor = findViewById(R.id.receptor);
            receptor.setText(message.sender);
            EditText object = findViewById(R.id.object);
            object.setText("Réponse: " + message.object);
        }

        Button buttonEnvoyer = findViewById(R.id.button_send);
        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText receptor = findViewById(R.id.receptor);
                String receptorText = receptor.getText().toString();
                EditText object = findViewById(R.id.object);
                String objectText = object.getText().toString();
                EditText content = findViewById(R.id.content);
                String contentText = content.getText().toString();

                if(receptorText.isEmpty()){
                    Snackbar.make(view, "Le destinataire n'est pas défini", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(contentText.isEmpty()){
                    Snackbar.make(view, "Le message n'est pas défini", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    /*Snackbar.make(view, "Le message a bien été envoyé", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    sendMessage(receptorText, objectText, contentText);

                    Toast.makeText(getApplicationContext(),"Le message a bien été envoyé",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    public boolean sendMessage(String receiver, String object, String content){
        boolean res = true;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        Map<String, Object> receiverData = new HashMap<>();
        receiverData.put("email", receiver);
        receiverData.put("vue", false);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("destinataires", receiverData);
        messageData.put("contenu", content);
        messageData.put("objet", object);
        messageData.put("date", df.format(dateobj));
        messageData.put("emetteur", Users.getCurrentUser().getEmail());

        db.collection("Messages")
                .add(messageData)
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
