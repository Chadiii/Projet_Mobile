package com.example.projetmobile.activity.emploi;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetmobile.R;
import com.example.projetmobile.Utils.LetterImageView;

public class EmploiViewHolder extends RecyclerView.ViewHolder {

    TextView matiere, enseignant, salle, horaire;
    LetterImageView letterImageView;
    View eView;

    public EmploiViewHolder(@NonNull View itemView) {
        super(itemView);
        eView=itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emploiClicklistener.OnItemClick(v,getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                emploiClicklistener.OnItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
        // initialize views
        matiere=itemView.findViewById(R.id.tvonsultsingleitemsubject);
        enseignant=itemView.findViewById(R.id.tvonsultsingleitemens);
        salle=itemView.findViewById(R.id.tvonsultsingleitemsalle);
        horaire=itemView.findViewById(R.id.tvonsultsingleitemtime);
        letterImageView=itemView.findViewById(R.id.ivdayconsultsingleitem);
    }
    private EmploiViewHolder.ClickListener emploiClicklistener;
    //interface for click listener
    public  interface  ClickListener{
        void OnItemClick(View view, int position);
        void OnItemLongClick(View view, int position);
    }
    public void setOnClickListener(EmploiViewHolder.ClickListener clickListener){
        emploiClicklistener = clickListener;
    }

}

