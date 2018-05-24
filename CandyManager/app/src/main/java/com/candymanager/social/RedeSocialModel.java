package com.candymanager.social;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RedeSocialModel {

    private String id;
    private String picture;
    private String fullPicture;
    private int likes;
    private int comments;
    private String createdTime;
    private String description;
    private int type;
    private boolean facebookPost = false;
    private boolean instagramPost = false;


    public void setFacebookPost(boolean facebookPost){
        this.facebookPost = facebookPost;
    }

    public boolean isFacebookPost() {
        return facebookPost;
    }

    public boolean isInstagramPost() {
        return instagramPost;
    }

    public void setInstagramPost(boolean instagramPost) {
        this.instagramPost = instagramPost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFullPicture() {
        return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getCreatedTime() {
        return createdTime;
    }


    public void setCreatedTime(String createdTime) {
        if (createdTime != null) {

            if (type == 1) {
                try {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date date = dateFormat.parse(createdTime);

                    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    createdTime = dateFormat.format(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(type == 2){
                Date date = new Date(Long.parseLong(createdTime) * 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                createdTime = dateFormat.format(date);


            }
        }

        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description.trim().equals("") ? "Sem descrição" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
