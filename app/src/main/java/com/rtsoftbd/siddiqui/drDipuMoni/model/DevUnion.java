/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

/**
 * Created by sazal on 2017-06-20.
 */

public class DevUnion {
    int unionID;
    String unionName;
    DevUpozila devUpozila;

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
