package com.example.projetmobile.activity.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.R;
import com.example.projetmobile.activity.messagerie.NewMessage;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfilActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    private Button changeProfileButton;
    private TableRow levelRow;
    private TextView userNom, userPrenom, userMail, userTel, userRole, userLevel;
    private Users targetedUser;

    private LinearLayout emptyGroup, progressBar, mainContent;
    private TextView emptyText;
    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        progressBar = findViewById(R.id.profile_progress_bar);
        emptyGroup = findViewById(R.id.profile_empty_box_group);
        mainContent = findViewById(R.id.profile_main_content);
        emptyText = findViewById(R.id.profile_empty_box_text);

        levelRow = (TableRow) findViewById(R.id.profile_level_row);
        changeProfileButton = (Button) findViewById(R.id.profile_image_change_btn);
        userNom = (TextView) findViewById(R.id.profile_nom);
        userPrenom = (TextView) findViewById(R.id.profile_prenom);
        userMail = (TextView) findViewById(R.id.profile_email);
        userTel = (TextView) findViewById(R.id.profile_tel);
        userRole = (TextView) findViewById(R.id.profile_role);
        userLevel = (TextView) findViewById(R.id.profile_level);

        Intent i = getIntent();
        final String targetedUserMail = (String) i.getSerializableExtra("userMail");


        if(targetedUserMail==null) finish();
        else if (targetedUserMail.compareTo(Users.getCurrentUser().getEmail())==0){
            setDataOnView(Users.getCurrentUser());
            progressBar.setVisibility(View.GONE);
        }
        else{
            Button messageButton = findViewById(R.id.profile_button_new_message);
            messageButton.setVisibility(View.VISIBLE);
            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NewMessage.class);
                    intent.putExtra("receptor", targetedUserMail);
                    startActivity(intent);
                }
            });
            searchUser(targetedUserMail);
        }

        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePicture();
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void setDataOnView(final Users user){
        if(user==null) finish();
        userNom.setText(user.getNom());
        userPrenom.setText(user.getPrenom());
        userMail.setText(user.getEmail());
        userTel.setText(user.getTelephone());
        userRole.setText(user.getRole());
        if(user.getRole().compareTo("Professeur")==0)
            levelRow.setVisibility(View.GONE);
        else
            userLevel.setText(user.level+"A");
        if(user.getEmail().compareTo(Users.getCurrentUser().getEmail())!=0)
            changeProfileButton.setVisibility(View.GONE);

        if(user.getPicture() != null)
            Picasso.get()
                    .load(user.getPicture())
                    //.load("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .resize(130, 130)
                    .centerCrop()
                    .error(R.drawable.profile)
                    .into((CircleImageView) findViewById(R.id.user_profile_picture_image));

        ((CircleImageView) findViewById(R.id.user_profile_picture_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getPicture() != null){
                    Intent intent = new Intent(view.getContext(), ProfilPictureActivity.class);
                    intent.putExtra("imageUrl", user.getPicture());
                    startActivity(intent);
                }
            }
        });

        mainContent.setVisibility(View.VISIBLE);
    }

    public void searchUser(String email){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> userData = document.getData();
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
                                targetedUser = new Users(userEmail, userNom, userPrenom, userPhone, role, level, picture);
                            }
                            if(targetedUser == null){
                                emptyText.setText("Cet utilisateur n'a pas été trouvé");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                setDataOnView(targetedUser);
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

    public void changeProfilePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                final StorageReference ref = storageReference.child("profilePictures/"+ Users.getCurrentUser().getEmail()+"_picture");

                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUrl = uri;
                                        Log.d("picture url",downloadUrl.toString());
                                                //Do what you want with the url
                                        setNewPictureUrl(downloadUrl.toString());
                                    }
                                });
                                progressDialog.dismiss();
                                Toast.makeText(ProfilActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(ProfilActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
        }
    }

    public void setNewPictureUrl(String url){
        Users.getCurrentUser().setPicture(url);
        Picasso.get()
                .load(url)
                .resize(130, 130)
                .centerCrop()
                .error(R.drawable.profile)
                .into((CircleImageView) findViewById(R.id.user_profile_picture_image));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(Users.getCurrentUser().getId()).update("picture",url);

    }
}
