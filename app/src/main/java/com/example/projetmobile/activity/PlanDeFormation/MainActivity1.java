package com.example.projetmobile.activity.PlanDeFormation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private EditText editTextQty;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextQty = findViewById(R.id.edittext_qty);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_products).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String name, String brand, String desc, String qty) {
        if (name.isEmpty()) {
            editTextName.setError("Semestre required");
            editTextName.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            editTextBrand.setError("Code required");
            editTextBrand.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editTextDesc.setError("Intitulé required");
            editTextDesc.requestFocus();
            return true;
        }



        if (qty.isEmpty()) {
            editTextQty.setError("Volume required");
            editTextQty.requestFocus();
            return true;
        }
        return false;
    }


    private void saveProduct(){
        String name = editTextName.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String qty = editTextQty.getText().toString().trim();

        if (!hasValidationErrors(name, brand, desc, qty)) {

            CollectionReference dbProducts = db.collection("Plan de Formation");

            Product product = new Product(
                    name,
                    brand,
                    desc,
                    Integer.parseInt(qty)
            );

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity1.this, " Module ajouté", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity1.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.button_save:
                saveProduct();
                break;
            case R.id.textview_view_products:
                startActivity(new Intent(this, ProductsActivity.class));
                break;
        }

    }
}
