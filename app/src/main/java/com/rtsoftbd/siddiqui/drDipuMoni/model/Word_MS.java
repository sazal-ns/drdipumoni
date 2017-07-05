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

public class Word_MS {
    int id;
    String word_name;
    Union_MS union_ms;

    static List<Word_MS> word_msList = new ArrayList<>();

    public static List<Word_MS> getWord_msList() {
        return word_msList;
    }

    public static void setWord_msList(Word_MS word_msList) {
        Word_MS.word_msList.add(word_msList);
    }

    public Word_MS() {
    }

    public Word_MS(int id, String word_name, Union_MS union_ms) {
        this.id = id;
        this.word_name = word_name;
        this.union_ms = union_ms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public Union_MS getUnion_ms() {
        return union_ms;
    }

    public void setUnion_ms(Union_MS union_ms) {
        this.union_ms = union_ms;
    }

    @Override
    public String toString() {
        return word_name;
    }
}
