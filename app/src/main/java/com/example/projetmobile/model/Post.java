package com.example.projetmobile.model;


import java.io.Serializable;

public class Post implements Serializable,Comparable  {
    public String id, user, userMail, content, date;
    public Boolean toTeacher, toStudent1, toStudent2, toStudent3;

    public Post(String id, String user, String userMail, String content, String date, Boolean toTeacher, Boolean toStudent1, Boolean toStudent2, Boolean toStudent3){
        this.id = id;
        this.user = user;
        this.userMail = userMail;
        this.content = content;
        this.date = date;
        this.toTeacher = toTeacher;
        this.toStudent1 = toStudent1;
        this.toStudent2 = toStudent2;
        this.toStudent3 = toStudent3;
    }

    public String getVisibility(){
        String visibility = "Visible par: ";
        if(this.toTeacher) visibility+="Professeurs ";
        if(this.toStudent1 || this.toStudent2 || this.toStudent3) visibility+="Elève ";
        if(this.toStudent1) visibility+="1A ";
        if(this.toStudent2) visibility+="2A ";
        if(this.toStudent3) visibility+="3A";

        return visibility;

    }

    @Override
    public int compareTo(Object o) {
        Post post = (Post) o;
        return this.date.compareTo(post.date);
    }
}
