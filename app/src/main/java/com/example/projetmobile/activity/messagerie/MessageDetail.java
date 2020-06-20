package com.example.projetmobile.activity.messagerie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.activity.users.ProfilActivity;
import com.example.projetmobile.model.Message;
import com.example.projetmobile.model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MessageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        final Message message = (Message)i.getSerializableExtra("message");

        TextView sender = findViewById(R.id.sender);
        if(message.sender.compareTo(Users.getCurrentUser().getEmail()) != 0)
            sender.setText(message.sender);
        else{
            sender.setText((String) message.receivers.get("email"));
            /*String[] receivers =(String[]) message.receivers.toArray(new String[message.receivers.size()]);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < receivers.length; j++) {
                sb.append(receivers[j]);
                if (j != receivers.length - 1) {
                    sb.append(", ");
                }
            }
            sender.setText(sb.toString());*/

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.INVISIBLE);
        }

        TextView object = findViewById(R.id.object);
        object.setText(message.object);

        TextView date = findViewById(R.id.date);
        date.setText(message.date);

        TextView content = findViewById(R.id.content);
        content.setText(message.content);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessage.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }
        });

        Button senderProfil = findViewById(R.id.message_sender_profil_button);
        senderProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String targetedEmail;
                if(message.sender.compareTo(Users.getCurrentUser().getEmail()) != 0) //message recus
                    targetedEmail = message.sender;
                else //message envoyé
                    targetedEmail = (String) message.receivers.get("email");

                Intent intent =  new Intent(getApplicationContext(), ProfilActivity.class);
                intent.putExtra("userMail", targetedEmail);
                startActivity(intent);
            }
        });
    }
}
