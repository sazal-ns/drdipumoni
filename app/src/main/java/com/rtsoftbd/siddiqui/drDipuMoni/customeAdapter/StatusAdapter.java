/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.customeAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rtsoftbd.siddiqui.drDipuMoni.R;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Status;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RTsoftBD_Siddiqui on 2017-06-05.
 */

public class StatusAdapter extends BaseAdapter {

    List<Status> statusList = new ArrayList<>();
    Activity activity;
    LayoutInflater layoutInflater;
    int who;

    public StatusAdapter(List<Status> statusList, Activity activity, int who) {
        this.statusList = statusList;
        this.activity = activity;
        this.who = who;
    }

    @Override
    public int getCount() {
        return statusList.size();
    }

    @Override
    public Object getItem(int position) {
        return statusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.status, null);

        Status status = statusList.get(position);

        ViewHolder holder = new ViewHolder(convertView);
        holder.ms_Status.setText(status.getStatus());
        if (who==1) holder.ms_Date.setText(status.get_date());
        else {
            holder.ms_Date.setTextColor(activity.getResources().getColor(android.R.color.black));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.status)
        AppCompatTextView ms_Status;
        @BindView(R.id.date)
        AppCompatTextView ms_Date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
