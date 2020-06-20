package com.example.projetmobile.activity.Datacentre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetmobile.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Cours extends AppCompatActivity {


    EditText editPDFName;
    Button btn_upload;
    private Toolbar toolbar;


    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        setupUIViews();
        initToolbar();

        editPDFName = (EditText)findViewById(R.id.txt_pdfName);
        btn_upload = (Button)findViewById(R.id.btn_upload);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Datacentre");

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nompdf = editPDFName.getText().toString().trim();

                if (!hasValidationErrorss(nompdf)) {

                    selectPDFFile();}

            }
        });




    }

    private boolean hasValidationErrorss(String nompdf) {
        if (nompdf.isEmpty()) {
            editPDFName.setError("Veuillez entrer le nom du fichier à uploader");
            editPDFName.requestFocus();
            return true;
        }
        return false;
    }

    private void selectPDFFile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data !=null && data.getData()!=null) {

            uploadPDFFile(data.getData());


        }

    }

    private void uploadPDFFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("Datacentre/"+System.currentTimeMillis()+" .pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url= uri.getResult();

                        uploadPDF uploadPDF = new uploadPDF(editPDFName.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(Cours.this,"File Uploaded",Toast.LENGTH_SHORT);
                        progressDialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Uploaded: "+(int)progress+"%");




            }
        });




    }


    public void btn_action(View view) {
        startActivity(new Intent(getApplicationContext(), View_PDF_Files.class));
    }

    private void setupUIViews() {
        toolbar = (Toolbar) findViewById(R.id.Toolbardatacentre);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DATACENTRE I.W.I.M");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}



