/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.customeAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rtsoftbd.siddiqui.drDipuMoni.R;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Resume;

import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-15.
 */

public class CustomListAdapterLatest extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Resume> resumes;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapterLatest(Activity activity, List<Resume> resumes) {
        this.activity = activity;
        this.resumes = resumes;
    }

    @Override
    public int getCount() {
        return resumes.size();
    }

    @Override
    public Object getItem(int position) {
        return resumes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.row_latest_update, null);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.latestNetworkImageView);
        TextView head = (TextView) convertView.findViewById(R.id.latestHeadTextView);
        TextView body = (TextView) convertView.findViewById(R.id.latestBodyTextView);

        Resume resume = resumes.get(position);

        thumbnail.setImageUrl(ApiUrl.ASSETS_LATEST_UPDATE+resume.getPictureString(), imageLoader);
        head.setText(resume.getTitle());
        body.setText(resume.getDetails());



        return convertView;
    }
}
