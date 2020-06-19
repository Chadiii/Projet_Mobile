package com.example.projetmobile.activity.post;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.projetmobile.R;
import com.example.projetmobile.model.Post;
import com.example.projetmobile.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    Context context;
    ArrayList<Post> posts;
    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
        this.context = context;
        this.posts = posts;
    }

    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {
        final Post post = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
        }

        TextView userAvatar = convertView.findViewById(R.id.post_circle_avatar);
        TextView userName = convertView.findViewById(R.id.post_user_name);
        TextView date = convertView.findViewById(R.id.post_date);
        TextView visibility = convertView.findViewById(R.id.post_visibility);
        final TextView content = convertView.findViewById(R.id.post_content);
        Button deletePostButton = convertView.findViewById(R.id.delete_post_button);
        deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Posts").document(post.id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                posts.remove(position);
                                PostAdapter.this.notifyDataSetChanged();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        });

        userAvatar.setText(String.valueOf(post.user.charAt(0)));
        String txt = "";
        if(post.userMail != null && post.userMail.compareTo(Users.getCurrentUser().getEmail())==0) {
            txt = " (Vous)";
            deletePostButton.setVisibility(View.VISIBLE);
        }
        else
            deletePostButton.setVisibility(View.GONE);
        userName.setText(post.user+txt);
        date.setText(post.date);
        content.setText(post.content);
        visibility.setText(post.getVisibility());

        return convertView;
    }


}
