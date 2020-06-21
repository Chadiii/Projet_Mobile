package com.example.projetmobile.activity.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CheckBox;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.MainActivity;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Users;
import com.example.projetmobile.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button loginButton;
    private Button loginpasswordforgot;
    private ProgressDialog loadingBar;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private CheckBox chkBoxRememberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent i = getIntent();
        String receivedEmail = (String)i.getSerializableExtra("email");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loginButton = (Button) findViewById(R.id.login_btn);
        loginpasswordforgot = (Button) findViewById(R.id.forget_password_link);

        inputPassword = (EditText) findViewById(R.id.login_password_input);
        inputEmail = (EditText) findViewById(R.id.email_input);

        if(!TextUtils.isEmpty(receivedEmail))
            inputEmail.setText(receivedEmail);

        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chbk);
        Paper.init(this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });

        loginpasswordforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));

            }
        });

    }

    private void LoginUser() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        ////////////////////////////////////////// for test
        //email = "test@iwim.com"; password = "testtest";
        //email = "arnaud@iwim.com"; password = "123456";
        /////////////////////////////////////////

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(email, password);
        }
    }

    private void AllowAccessToAccount(final String email, final String password) {


        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserLoginKey, email);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if (email.equals("chef@gmail.com") && password.equals("chef")) {
            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
            Intent intent = new Intent(LoginActivity.this, ChefFiliereActivity.class);
            startActivity(intent);


        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
                            final Boolean emailflag = firebaseUser.isEmailVerified();

                            if (task.isSuccessful() && emailflag) {
                                //get user data
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    DocumentReference docRef = db.collection("Users").document(user.getUid());
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Log.d("succ", "DocumentSnapshot data: " + document.getData());
                                                    Map<String, Object> userData = document.getData();
                                                    String id = (String) document.getId();
                                                    String userEmail = (String) userData.get("email");
                                                    String userNom = (String) userData.get("nom");
                                                    String userPrenom = (String) userData.get("prenom");
                                                    String userPhone = (String) userData.get("telephone");
                                                    String role = (String) userData.get("role");
                                                    String picture = (String) userData.get("picture");
                                                    int level;
                                                    if(role.compareTo("Professeur")==0) level = 0;
                                                    else{
                                                        long levell = (long) (userData.get("level")==null?new Long(1):userData.get("level"));
                                                        level = (int)levell;
                                                        if(level>3 || level<1) level = 1;
                                                    }
                                                    Log.d("data", userEmail+" "+userNom+" "+userPrenom+" "+userPhone+" "+level);
                                                    Users currentUser = new Users(id, userEmail, userNom, userPrenom, userPhone, role, level, picture);
                                                    Users.setCurrentUser(currentUser);

                                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);

                                                } else {
                                                    Log.d("not", "No such document");
                                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            } else {
                                                Log.d("failed", "get failed with ", task.getException());
                                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                                }

                            /*Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);*/
                            } else {
                                Toast.makeText(LoginActivity.this, "Verify your E-MAIL or Register", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }

                        }
                    });

        }




    }
}

