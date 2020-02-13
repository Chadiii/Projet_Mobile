package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.model.Users;
import com.example.projetmobile.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText InputLogin, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private  String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputLogin = (EditText) findViewById(R.id.login_input);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });
    }

    private void LoginUser() {

        String login = InputLogin.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(login)){
            Toast.makeText(this, "Please write your Login...", Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(login, password);
        }
    }

    private void AllowAccessToAccount(final String login, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(login).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(login).getValue(Users.class);

                    if(usersData.getLogin().equals(login)){

                        if(usersData.getPassword().equals(password)){



                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);


                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Wrong Password, try again...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Focus on your password or try recovery", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Account with this "+login+" login do not exist. ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "you need to create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

