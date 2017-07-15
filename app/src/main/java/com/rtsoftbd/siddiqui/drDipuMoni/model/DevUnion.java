/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/15/17 4:38 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/15/17 12:56 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

/**
 * Created by sazal on 2017-06-20.
 */

public class DevUnion {
    int unionID;
    String unionName;
    int devUpozilaID;
    DevUpozila devUpozila;

    public int getDevUpozilaID() {
        return devUpozilaID;
    }

    public void setDevUpozilaID(int devUpozilaID) {
        this.devUpozilaID = devUpozilaID;
    }

    public DevUnion() {
    }

    public DevUnion(int unionID, String unionName, DevUpozila devUpozila) {
        this.unionID = unionID;
        this.unionName = unionName;
        this.devUpozila = devUpozila;
    }

    public int getUnionID() {
        return unionID;
    }

    public void setUnionID(int unionID) {
        this.unionID = unionID;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public DevUpozila getDevUpozila() {
        return devUpozila;
    }

    public void setDevUpozila(DevUpozila devUpozila) {
        this.devUpozila = devUpozila;
    }

    @Override
    public String toString() {
        return unionName;
    }
}
