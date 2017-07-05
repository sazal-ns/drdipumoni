/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

/**
 * Created by sazal on 2017-06-20.
 */

public class DevUpozila {
    int upozilaID;
    String upozilaName;

    public DevUpozila() {
    }

    public DevUpozila(int upozilaID, String upozilaName) {
        this.upozilaID = upozilaID;
        this.upozilaName = upozilaName;
    }

    public int getUpozilaID() {
        return upozilaID;
    }

    public void setUpozilaID(int upozilaID) {
        this.upozilaID = upozilaID;
    }

    public String getUpozilaName() {
        return upozilaName;
    }

    public void setUpozilaName(String upozilaName) {
        this.upozilaName = upozilaName;
    }

    @Override
    public String toString() {
        return upozilaName;
    }
}
