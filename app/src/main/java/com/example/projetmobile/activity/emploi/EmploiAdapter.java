package com.example.projetmobile.activity.emploi;


import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Tabletime;
import com.example.projetmobile.model.Users;
import java.util.ArrayList;
import java.util.List;

public class EmploiAdapter extends RecyclerView.Adapter<EmploiViewHolder> {

    private DetaildayconsultActivity context;;
    private List<Tabletime> tabletimelist;
    private ArrayList<String> cours;


    public EmploiAdapter(DetaildayconsultActivity context, List<Tabletime> tabletimelist, ArrayList<String> cours) {
        this.context = context;
        this.tabletimelist = tabletimelist;
        this.cours = cours;
    }

    @NonNull
    @Override
    public EmploiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_dayconsult_single_item,parent,false);

        EmploiViewHolder emploiViewHolder= new EmploiViewHolder(itemView);

        emploiViewHolder.setOnClickListener(new EmploiViewHolder.ClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

            }

            @Override
            public void OnItemLongClick(View view, final int position) {
                Users user;
                user = Users.getCurrentUser();
                if(user==null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        String[] options = {"Update", "Delete"};
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    //update
                                    String id = tabletimelist.get(position).getId();
                                    String matiere = tabletimelist.get(position).getMatiere();
                                    String salle = tabletimelist.get(position).getSalle();
                                    String enseignet = tabletimelist.get(position).getEnseignant();
                                    String heuredebut = tabletimelist.get(position).getHeuredebut();
                                    String heurefin = tabletimelist.get(position).getHeurefin();
                                    Intent intent = new Intent(context, DayDetailActivity.class);
                                    //put data in Intent
                                    intent.putExtra("uid", id);
                                    intent.putExtra("umatiere", matiere);
                                    intent.putExtra("usalle", salle);
                                    intent.putExtra("uenseignet", enseignet);
                                    intent.putExtra("heuredebut", heuredebut);
                                    intent.putExtra("heurefin", heurefin);
                                    context.startActivity(intent);

                                }
                                if (which == 1) {
                                   context.deleteData(position);
                                }
                            }
                        }).create().show();
                    }
                }
        });
        return emploiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmploiViewHolder holder, int position) {



        holder.matiere.setText(tabletimelist.get(position).getMatiere());
        holder.enseignant.setText(tabletimelist.get(position).getEnseignant());
        holder.salle.setText(tabletimelist.get(position).getSalle());
        holder.horaire.setText(tabletimelist.get(position).getHeuredebut()+"-"+tabletimelist.get(position).getHeurefin());
        holder.letterImageView.setOval(true);
        holder.letterImageView.setLetter(cours.get(position).charAt(0));
    }

    @Override
    public int getItemCount() {
        return tabletimelist.size();
    }
}
