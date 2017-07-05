/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-17.
 */

public class User {

    public String voter_id;
    public String name;
    public String phone;
    public String email;
    public String gender;
    public String upozila;
    public String upo_union;
    public String word_cha;
    public String type;
    public String created_on;
    public String password;
    public String image,nid,deg;

    public String getImage() {
        return image;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User() {
    }

    public User(String voter_id, String name, String phone, String email, String gender, String upozila, String upo_union, String word_cha, String type, String created_on, String password, String image) {
        this.voter_id = voter_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.upozila = upozila;
        this.upo_union = upo_union;
        this.word_cha = word_cha;
        this.type = type;
        this.created_on = created_on;
        this.password = password;
        this.image = image;
    }

    public String getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(String voter_id) {
        this.voter_id = voter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUpozila() {
        return upozila;
    }

    public void setUpozila(String upozila) {
        this.upozila = upozila;
    }

    public String getUpo_union() {
        return upo_union;
    }

    public void setUpo_union(String upo_union) {
        this.upo_union = upo_union;
    }

    public String getWord_cha() {
        return word_cha;
    }

    public void setWord_cha(String word_cha) {
        this.word_cha = word_cha;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
