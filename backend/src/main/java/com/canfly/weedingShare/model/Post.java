package com.canfly.weedingShare.model;

import java.time.LocalDateTime;

public class Post {

    private String id;
    private String imageUrl;
    private String comment;
    private final LocalDateTime createdAt;

    public Post() {
        this.createdAt = LocalDateTime.now();
    }

    public Post(String id, String imageUrl, String comment) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
