package com.example.projetmobile.activity.messagerie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Message;
import com.example.projetmobile.model.Users;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {
    Context context;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Message message = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        }

        TextView sender = convertView.findViewById(R.id.main_text);
        TextView object = convertView.findViewById(R.id.second_text);

        //Message Recus
        if(message.sender.compareTo(Users.getCurrentUser().getEmail()) != 0)
            sender.setText(message.sender);
        else{//Message envoyés
            sender.setText((String) message.receivers.get("email"));
            /* Cas ou il ya plusieurs destinataire
            String[] receivers =(String[]) message.receivers.toArray(new String[message.receivers.size()]);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < receivers.length; i++) {
                sb.append(receivers[i]);
                if (i != receivers.length - 1) {
                    sb.append(", ");
                }
            }
            sender.setText(sb.toString());*/
        }

        object.setText(message.object+" - "+message.date);


        //new message unseen
        if (!message.hasSeen){
            LinearLayout myMessage = convertView.findViewById(R.id.message_item);
            myMessage.setBackgroundColor(0xFFDDDDDD);
            object.setTypeface(null, Typeface.BOLD);
            sender.setTypeface(null, Typeface.BOLD);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageDetail.class);
                intent.putExtra("message", message);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}