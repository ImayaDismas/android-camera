package com.dismas.imaya.android_camera;

/**
 * Created by imaya on 3/21/16.
 */
public class ImagePost {
    String Picture;
    String FireKey;

    public ImagePost(String picture,String fireKey,String votes) {

        this.Picture = picture;
        this.FireKey = fireKey;


    }

    public String getFireKey() {
        return FireKey;
    }

    public String getPicture() {
        return Picture;
    }

    public void setFireKey(String fireKey) {
        FireKey = fireKey;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
