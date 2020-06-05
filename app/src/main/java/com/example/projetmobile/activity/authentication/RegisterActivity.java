package com.example.projetmobile.activity.authentication;

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

import com.example.projetmobile.MainActivity;
import com.example.projetmobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputPrenom, InputNom, InputPassword, InputConfirmPassword, InputEmail, InputTelephone;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputNom = (EditText) findViewById(R.id.register_nom_input);
        InputPrenom = (EditText) findViewById(R.id.register_prenom_input);
        InputTelephone = (EditText) findViewById(R.id.register_telephone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_input);

        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = InputEmail.getText().toString();
                String nom = InputNom.getText().toString();
                String prenom = InputPrenom.getText().toString();
                String telephone = InputTelephone.getText().toString();
                String password = InputPassword.getText().toString();
                String confirmPassword = InputConfirmPassword.getText().toString();



                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Please write your email ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(nom)){
                    Toast.makeText(RegisterActivity.this, "Please write your nom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(prenom)){
                    Toast.makeText(RegisterActivity.this, "Please write your prenom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(telephone)){
                    Toast.makeText(RegisterActivity.this, "Please write your phone number ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please write your password ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Please confirm your password ...", Toast.LENGTH_SHORT).show();
                }
                else if(confirmPassword.compareTo(password) != 0){
                    Toast.makeText(RegisterActivity.this, "Your password does not match ...", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidateLogin(email, nom, prenom, telephone, password);
                }
            }
        });


    }

    private void ValidateLogin(final String email, final String nom, final String prenom, final String telephone,final String password) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("nom", nom);
                            userData.put("prenom", prenom);
                            userData.put("telephone", telephone);

                            db.collection("Users").document(user.getUid())
                                    .set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error writing document", e);
                                        }
                                    });

                            Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            loadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}