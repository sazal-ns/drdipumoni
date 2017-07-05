/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-06-06.
 */

public class Upozila_MS {
    int id;
    String upozila_name;

    static List<Upozila_MS> upozila_msList = new ArrayList<>();

    public static List<Upozila_MS> getUpozila_msList() {
        return upozila_msList;
    }

    public static void setUpozila_msList(Upozila_MS upozila_msList) {
        Upozila_MS.upozila_msList.add(upozila_msList);
    }

    public Upozila_MS() {
    }

    public Upozila_MS(int id, String upozila_name) {
        this.id = id;
        this.upozila_name = upozila_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpozila_name() {
        return upozila_name;
    }

    public void setUpozila_name(String upozila_name) {
        this.upozila_name = upozila_name;
    }

    @Override
    public String toString() {
        return upozila_name;
    }
}
