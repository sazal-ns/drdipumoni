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

public class Union_MS {
    int id;
    String union_name;
    Upozila_MS upozila_ms;

    static List<Union_MS> union_msList = new ArrayList<>();

    public static List<Union_MS> getUnion_msList() {
        return union_msList;
    }

    public Union_MS() {
    }

    public static void setUnion_msList(Union_MS union_msList) {
        Union_MS.union_msList.add(union_msList);
    }

    public Union_MS(int id, String union_name, Upozila_MS upozila_ms) {
        this.id = id;
        this.union_name = union_name;
        this.upozila_ms = upozila_ms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnion_name() {
        return union_name;
    }

    public void setUnion_name(String union_name) {
        this.union_name = union_name;
    }

    public Upozila_MS getUpozila_ms() {
        return upozila_ms;
    }

    public void setUpozila_ms(Upozila_MS upozila_ms) {
        this.upozila_ms = upozila_ms;
    }

    @Override
    public String toString() {
        return union_name;
    }
}
