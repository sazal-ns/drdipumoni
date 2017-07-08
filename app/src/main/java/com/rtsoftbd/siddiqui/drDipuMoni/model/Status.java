/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/8/17 3:16 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/6/17 1:37 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni.model;

import java.io.Serializable;



public class Status implements Serializable {
   private int id, is_active, upzila_id, union_id;
    private String status, _date;

    public Status() {
    }

    public Status(int id, int is_active, String status, String _date) {
        this.id = id;
        this.is_active = is_active;
        this.status = status;
        this._date = _date;
    }

    public int getUpzila_id() {
        return upzila_id;
    }

    public void setUpzila_id(int upzila_id) {
        this.upzila_id = upzila_id;
    }

    public int getUnion_id() {
        return union_id;
    }

    public void setUnion_id(int union_id) {
        this.union_id = union_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }


}
