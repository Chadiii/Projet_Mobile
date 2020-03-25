package com.example.projetmobile.model;

import java.io.Serializable;
import java.util.Map;

public class Message implements Serializable,Comparable  {
    public String id;
    public String object;
    public String sender;
    public String content;
    //public List<String> receivers;
    public boolean hasSeen;
    public String date;
    public Map<String, Object> receivers;

    public Message(String id, String object, String sender, String content,  Map<String, Object> receivers, boolean hasSeen, String date){
        this.id = id;
        this.object = object;
        this.sender = sender;
        this.content = content;
        this.receivers = receivers;
        this.hasSeen = hasSeen;
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        Message mess = (Message) o;
        return this.date.compareTo(mess.date);
    }
}