/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

import android.graphics.Bitmap;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-15.
 */

public  class Resume {
    String title, details, place, pictureString;
    Bitmap picture;

    public Resume() {
    }

    public Resume(String title, String details, String place, String pictureString) {
        this.title = title;
        this.details = details;
        this.place = place;
        this.pictureString = pictureString;
    }

    public Resume(String title, String details, String place, Bitmap picture) {
        this.title = title;
        this.details = details;
        this.place = place;
        this.picture = picture;
    }

    public Resume(String title, String details, String place, String pictureString, Bitmap picture) {
        this.title = title;
        this.details = details;
        this.place = place;
        this.pictureString = pictureString;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPictureString() {
        return pictureString;
    }

    public void setPictureString(String pictureString) {
        this.pictureString = pictureString;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
