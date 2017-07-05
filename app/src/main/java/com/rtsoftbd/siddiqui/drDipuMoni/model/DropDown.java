/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-06-04.
 */

public class DropDown {
    static List<WordUpozilaDesignation> _word, _upozila, _designation = new ArrayList<>();

    public DropDown() {
    }

    public static List<WordUpozilaDesignation> get_word() {
        return _word;
    }

    public static void set_word(WordUpozilaDesignation _word) {
        DropDown._word.add(_word);
    }

    public static List<WordUpozilaDesignation> get_upozila() {
        return _upozila;
    }

    public static void set_upozila(WordUpozilaDesignation _upozila) {
        DropDown._upozila.add(_upozila);
    }

    public static List<WordUpozilaDesignation> get_designation() {
        return _designation;
    }

    public static void set_designation(WordUpozilaDesignation _designation) {
        DropDown._designation.add(_designation);
    }
}
