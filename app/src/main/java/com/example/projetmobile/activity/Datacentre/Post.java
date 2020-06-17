package com.example.projetmobile.activity.Datacentre;

public class Post {
    String title;
    String url;
    String name;

    public Post() {
    }

    @Override
    public String toString() {
        return "uploads{ url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
