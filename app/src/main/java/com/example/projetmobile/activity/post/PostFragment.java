package com.example.projetmobile.activity.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Post;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PostFragment extends Fragment {
    private ArrayList<Post> arrayOfPosts;
    private PostAdapter adapter;
    private ListView listView;
    private LinearLayout emptyGroup;
    private LinearLayout progressBar;
    private TextView emptyText;
    private Button newPostButton;
    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance() {
        return (new PostFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_welcome, null);


        progressBar = root.findViewById(R.id.post_list_progress_bar);
        emptyGroup = root.findViewById(R.id.post_empty_box_group);
        emptyText = root.findViewById(R.id.post_empty_box_text);
        newPostButton = root.findViewById(R.id.create_new_post_button);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });



        listView =  root.findViewById(R.id.lv_post_list);
        adapter = new PostAdapter(root.getContext(), arrayOfPosts);
        searchPosts();


        return root;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    public void searchPosts(){
        arrayOfPosts = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userGroup = "toTeacher";
        Users currentUser = Users.getCurrentUser();
        if(currentUser.getRole().compareTo("Elève")==0){
            userGroup = "toStudent"+currentUser.level;
            this.newPostButton.setVisibility(View.GONE);
        }

        db.collection("Posts")
                .whereEqualTo(userGroup, true)
                //.whereEqualTo("userMail", Users.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> postData = document.getData();
                                arrayOfPosts.add(new Post(
                                        document.getId(),
                                        (String) postData.get("user"),
                                        (String) postData.get("userMail"),
                                        (String) postData.get("content"),
                                        (String) postData.get("date"),
                                        (Boolean) postData.get("toTeacher"),
                                        (Boolean) postData.get("toStudent1"),
                                        (Boolean) postData.get("toStudent2"),
                                        (Boolean) postData.get("toStudent3")
                                ));
                            }
                            /*if(arrayOfPosts.size()==0){
                                emptyText.setText("Aucun post");
                                emptyGroup.setVisibility(View.VISIBLE);
                            }
                            else{
                                Collections.sort(arrayOfPosts, Collections.reverseOrder());
                                adapter = new PostAdapter(adapter.context, arrayOfPosts);
                                listView.setAdapter(adapter);
                            }
                            progressBar.setVisibility(View.GONE);*/
                        } /*else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            emptyText.setText("Une erreur s'est produite");
                            emptyGroup.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }*/

                        db.collection("Posts")
                                .whereEqualTo("userMail", Users.getCurrentUser().getEmail())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                                boolean found = false;
                                                Map<String, Object> postData = document.getData();
                                                for(Post post : arrayOfPosts)
                                                    if(post.id.compareTo(document.getId())==0) found = true;

                                                if(!found)
                                                    arrayOfPosts.add(new Post(
                                                            document.getId(),
                                                            (String) postData.get("user"),
                                                            (String) postData.get("userMail"),
                                                            (String) postData.get("content"),
                                                            (String) postData.get("date"),
                                                            (Boolean) postData.get("toTeacher"),
                                                            (Boolean) postData.get("toStudent1"),
                                                            (Boolean) postData.get("toStudent2"),
                                                            (Boolean) postData.get("toStudent3")
                                                    ));
                                            }
                                            if(arrayOfPosts.size()==0){
                                                emptyText.setText("Aucun post");
                                                emptyGroup.setVisibility(View.VISIBLE);
                                            }
                                            else{
                                                Collections.sort(arrayOfPosts, Collections.reverseOrder());
                                                adapter = new PostAdapter(adapter.context, arrayOfPosts);
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
                                }
                            );
                    }
                });
    }
}
