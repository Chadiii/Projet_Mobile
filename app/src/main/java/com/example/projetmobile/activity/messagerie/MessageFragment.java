package com.example.projetmobile.activity.messagerie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Message;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MessageFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mode; //messageSent or messageReceived
    //    private ProgressDialog loadingBar;
    private ArrayList<Message> arrayOfMessages;
    private MessageAdapter adapter;
    private ListView listView;
    private LinearLayout emptyGroup;
    private LinearLayout progressBar;
    private TextView emptyText;
    private MessagerieHome activity;


    public MessageFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        activity = (MessagerieHome) getActivity();
        mode = activity.getMode();

        progressBar = view.findViewById(R.id.message_list_progress_bar);
        emptyGroup = view.findViewById(R.id.empty_box_group);
        emptyText = view.findViewById(R.id.empty_box_text);

        FloatingActionButton fab = view.findViewById(R.id.fab_new_message);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewMessage.class);
                startActivity(intent);
            }
        });


        listView =  view.findViewById(R.id.lv_messages_list);

        if (mode.compareTo("messageSent")==0){
            adapter = new MessageAdapter(view.getContext(), arrayOfMessages, true);
            searchSentMessages();
        }
        else{
            adapter = new MessageAdapter(view.getContext(), arrayOfMessages, false);
            searchReceivedMessages();
        }


        return view;
    }


    public void searchReceivedMessages(){
        arrayOfMessages = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Messages")
                .whereEqualTo("destinataires.email", Users.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> dest;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> messageData = document.getData();
                                dest = (Map<String, Object>) messageData.get("destinataires");
                                Boolean vue = (Boolean) dest.get("vue");
                                if(mode.compareTo("messageNewReceived")==0 && !vue)
                                    arrayOfMessages.add(new Message(
                                            document.getId(),
                                            (String)messageData.get("objet"),
                                            (String)messageData.get("emetteur"),
                                            (String)messageData.get("contenu"),
                                            dest,
                                            (Boolean) dest.get("vue"),
                                            (String)messageData.get("date")));
                                else if(mode.compareTo("messageReceived")==0 && vue)
                                    arrayOfMessages.add(new Message(
                                            document.getId(),
                                            (String)messageData.get("objet"),
                                            (String)messageData.get("emetteur"),
                                            (String)messageData.get("contenu"),
                                            dest,
                                            (Boolean) dest.get("vue"),
                                            (String)messageData.get("date")));

                            }
                            activity.setMessagesNumber(arrayOfMessages.size());
                            if(arrayOfMessages.size()==0){
                                if(mode.compareTo("messageNewReceived")==0)
                                    emptyText.setText("Aucun message non lu");
                                else
                                    emptyText.setText("Aucun message recu");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                Collections.sort(arrayOfMessages, Collections.reverseOrder());
                                adapter = new MessageAdapter(adapter.context, arrayOfMessages, false);
                                listView.setAdapter(adapter);
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

    public void searchSentMessages(){
        arrayOfMessages = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Messages")
                .whereEqualTo("emetteur", Users.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> dest;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> messageData = document.getData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                dest = (Map<String, Object>) messageData.get("destinataires");
                                arrayOfMessages.add(new Message(
                                        document.getId(),
                                        (String)messageData.get("objet"),
                                        (String)messageData.get("emetteur"),
                                        (String)messageData.get("contenu"),
                                        dest,
                                        (Boolean) dest.get("vue"),
                                        (String)messageData.get("date")));
                            }
                            activity.setMessagesNumber(arrayOfMessages.size());
                            if(arrayOfMessages.size()==0) {
                                emptyText.setText("Aucun message envoyé");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                Collections.sort(arrayOfMessages, Collections.reverseOrder());
                                adapter = new MessageAdapter(adapter.context, arrayOfMessages, true);
                                listView.setAdapter(adapter);
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
}
