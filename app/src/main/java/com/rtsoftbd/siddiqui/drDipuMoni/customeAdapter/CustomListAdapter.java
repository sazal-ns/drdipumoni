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

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Resume> resumes;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private int who;

    public CustomListAdapter(Activity activity, List<Resume> resumes, int who) {
        this.activity = activity;
        this.resumes = resumes;
        this.who = who;
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
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.row_education, null);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.picImageView);
        TextView head = (TextView) convertView.findViewById(R.id.headTextView);
        TextView body = (TextView) convertView.findViewById(R.id.bodyTextView);
        TextView subBody = (TextView) convertView.findViewById(R.id.subBodyTextView);

        Resume resume = resumes.get(position);


        head.setText(resume.getTitle());
        body.setText(resume.getDetails());
        subBody.setText(resume.getPlace());

        if (who==1) thumbnail.setImageUrl(ApiUrl.ASSETS_RESUME+resume.getPictureString(), imageLoader);
        else if (who == 2){
            thumbnail.setImageUrl(ApiUrl.ASSETS_ACHIVEMENT+resume.getPictureString(), imageLoader);
            subBody.setVisibility(View.GONE);
        }else if (who == 3){
            thumbnail.setImageUrl("http://maxpixel.freegreatpicture.com/static/photo/1x/Graduate-Graduation-Cap-Graduation-Education-Icon-1719741.png", imageLoader);
            subBody.setVisibility(View.GONE);
        }


        return convertView;
    }
}
