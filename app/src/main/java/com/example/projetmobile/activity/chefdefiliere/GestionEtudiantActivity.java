package com.example.projetmobile.activity.chefdefiliere;

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

import com.example.projetmobile.R;
import com.example.projetmobile.activity.authentication.ChefFiliereActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GestionEtudiantActivity extends AppCompatActivity {

    private EditText emailProfesseur, nomProfesseur, prenomProfesseur, telephoneProfesseur, passwordProfesseur, confirmProfesseurPassword;
    private Button validerProfesseurBtn, listPtofesseurBtn;

    String pEmail, pNom, pPrenom, pTelephone;

    ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_professeur);

        //ActionBar actionBar = getSupportActionBar();

        emailProfesseur = findViewById(R.id.emailPro);
        nomProfesseur = findViewById(R.id.nomPro);
        prenomProfesseur = findViewById(R.id.prenomPro);
        telephoneProfesseur = findViewById(R.id.telephonePro);
        passwordProfesseur = findViewById(R.id.passwordPro);
        confirmProfesseurPassword = findViewById(R.id.confpasswordPro);
        validerProfesseurBtn = findViewById(R.id.validButProf);
        listPtofesseurBtn = findViewById(R.id.listButProf);

        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle("Update Data");
            validerProfesseurBtn.setText("Valider les modifications");
            pEmail = bundle.getString("pEmail");
            pNom = bundle.getString("pNom");
            pPrenom = bundle.getString("pPrenom");
            pTele = bundle.getString("pEmail");

        }else {
            actionBar.setTitle("Add Data");
            validerProfesseurBtn.setText("Valider la création");
        }
        */
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        validerProfesseurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailProfesseur.getText().toString();
                String nom = nomProfesseur.getText().toString();
                String prenom = prenomProfesseur.getText().toString();
                String telephone = telephoneProfesseur.getText().toString();
                String password = passwordProfesseur.getText().toString();
                String confirmPassword = confirmProfesseurPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please write your email ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(nom)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please write your nom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(prenom)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please write your prenom ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(telephone)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please write your phone number ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please write your password ...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(GestionEtudiantActivity.this, "Please confirm your password ...", Toast.LENGTH_SHORT).show();
                }
                else if(confirmPassword.compareTo(password) != 0){
                    Toast.makeText(GestionEtudiantActivity.this, "Your password does not match ...", Toast.LENGTH_SHORT).show();
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


        listPtofesseurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionEtudiantActivity.this, listProfesseurActivity.class));
            }
        });

    }

    private void ValidateLogin(final String email, final String nom, final String prenom, final String telephone, final String password) {

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
                            userData.put("role", "Professeur");

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

                            Toast.makeText(GestionEtudiantActivity.this, "Congratulations, this account has been created", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent( GestionEtudiantActivity.this, ChefFiliereActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            loadingBar.dismiss();
                            Toast.makeText(GestionEtudiantActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
