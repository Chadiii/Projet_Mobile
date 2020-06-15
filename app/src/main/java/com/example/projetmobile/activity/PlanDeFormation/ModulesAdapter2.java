package com.example.projetmobile.activity.PlanDeFormation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Module;

import java.util.List;

public class ModulesAdapter2 extends RecyclerView.Adapter<ModulesAdapter2.ProductViewHolder> {

    private Context mCtx;
    private List<Module> moduleList;

    public ModulesAdapter2(Context mCtx, List<Module> modulelist) {
        this.mCtx = mCtx;
        this.moduleList = modulelist;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_module, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Module module = moduleList.get(position);

        holder.textViewSemestre.setText(module.getSemestre());
        holder.textViewCode.setText("Code: " +module.getCode());
        holder.textViewIntitule.setText( "Intitulé: "+module.getIntitule());
        holder.textViewVolume.setText("Volume :" + module.getVolume()+"Heures " );
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewSemestre, textViewCode, textViewIntitule,  textViewVolume;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewSemestre = itemView.findViewById(R.id.textview_semestre);
            textViewCode = itemView.findViewById(R.id.textview_code);
            textViewIntitule = itemView.findViewById(R.id.textview_intitule);
            textViewVolume = itemView.findViewById(R.id.textview_volume);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {



        }
    }
}

