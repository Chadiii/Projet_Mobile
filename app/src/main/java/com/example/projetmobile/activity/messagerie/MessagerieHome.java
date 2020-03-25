package com.example.projetmobile.activity.messagerie;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.projetmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MessagerieHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie_home);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessage.class);
                startActivity(intent);
            }
        });

        Button buttonReception = findViewById(R.id.button_reception);
        buttonReception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageList.class);
                intent.putExtra("mode","messageReceived");
                startActivity(intent);
            }
        });
        Button buttonEnvoi = findViewById(R.id.button_envoie);
        buttonEnvoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageList.class);
                intent.putExtra("mode","messageSent");
                startActivity(intent);
            }
        });
    }

}
