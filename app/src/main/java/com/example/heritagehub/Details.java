package com.example.heritagehub;

public class Details {
    private String title;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    private String subject;

    public Details(String title, String subject, String username, String description, String imageUrl) {
        this.title = title;
        this.subject = subject;
        Username = username;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    private String Username;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String description;


    private String imageUrl;
}
