package com.example.projetmobile.activity.chefdefiliere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    listProfesseurActivity ListProfesseurActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(listProfesseurActivity listProfesseurActivity, List<Model> modelList) {
        ListProfesseurActivity = listProfesseurActivity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // this will be called it user click item

                // show data in toast on clicking
                String nom = modelList.get(position).getNom();
                String prenom = modelList.get(position).getPrenom();
                String telephone = modelList.get(position).getTelephone();

                Toast.makeText(ListProfesseurActivity, nom + "\n" + prenom + "\n" + telephone , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                // this will be called it user long click item
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set data
        holder.mTelephone.setText(modelList.get(position).getTelephone());
        holder.mNom.setText(modelList.get(position).getNom());
        holder.mEmail.setText(modelList.get(position).getEmail1());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
