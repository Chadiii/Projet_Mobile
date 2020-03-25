package com.example.projetmobile.activity.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Users;

public class WelcomeFragment extends Fragment {
    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance() {
        return (new WelcomeFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_welcome, null);
        TextView welcome = root.findViewById(R.id.text_home);
        Users user = Users.getCurrentUser();
        if(user != null)
            welcome.setText("Welcome "+user.displayName());

        return root;
    }

}
