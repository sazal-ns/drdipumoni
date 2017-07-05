/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-06-04.
 */

public class WordUpozilaDesignation {
    int id, f_id;
    String name;

    public WordUpozilaDesignation() {
    }

    public WordUpozilaDesignation(int id, int f_id, String name) {
        this.id = id;
        this.f_id = f_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
