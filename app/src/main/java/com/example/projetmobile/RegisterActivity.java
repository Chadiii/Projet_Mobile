package com.example.projetmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputPrenom, InputNom, InputPassword, InputEmail, InputLogin;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputNom = (EditText) findViewById(R.id.register_nom_input);
        InputPrenom = (EditText) findViewById(R.id.register_prenom_input);
        InputLogin = (EditText) findViewById(R.id.register_login_input);

        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = InputNom.getText().toString();
                String prenom = InputPrenom.getText().toString();
                String login = InputLogin.getText().toString();
                String password = InputPassword.getText().toString();
                String email = InputEmail.getText().toString();


                if(TextUtils.isEmpty(nom)){
                    Toast.makeText(RegisterActivity.this, "Please write your nom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(prenom)){
                    Toast.makeText(RegisterActivity.this, "Please write your prenom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Please write your email ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please write your password ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(login)){
                    Toast.makeText(RegisterActivity.this, "Please write your login ...", Toast.LENGTH_SHORT).show();
                }

                else{
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidateLogin(login, email, nom, prenom, password);
                }
            }
        });


    }

    private void ValidateLogin(final String login, final String email, final String nom, final String prenom, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(login).exists())){

                    HashMap<String, Object> userDataMap = new HashMap<>();

                    userDataMap.put("login", login);
                    userDataMap.put("email", email);
                    userDataMap.put("nom", nom);
                    userDataMap.put("prenom", prenom);
                    userDataMap.put("password", password);

                    RootRef.child("Users").child(login).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                    }
                                    else{

                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time ...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "This " +login+ " Already exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another login", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
