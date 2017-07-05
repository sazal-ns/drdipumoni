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
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-17.
 */

public class CustomListAdapterUser extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<User> users;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapterUser(Activity activity, List<User> users) {
        this.activity = activity;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.row_user_list, null);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

        ViewHolder viewHolder = new ViewHolder(convertView);

        User user = users.get(position);

        viewHolder.ms_Image.setImageUrl(ApiUrl.ASSETS_UPLOAD+user.getImage(), imageLoader);
        viewHolder.ms_NameTextView.setText(user.getName());
        viewHolder.ms_UpozilaTextView.setText(user.getUpozila() + ",");
        viewHolder.ms_UnionTextView.setText(user.getUpo_union() + ",");
        viewHolder.ms_WordTextView.setText(user.getWord_cha());

        return convertView;
    }



    static class ViewHolder {
        @BindView(R.id.image) NetworkImageView ms_Image;
        @BindView(R.id.nameTextView) TextView ms_NameTextView;
        @BindView(R.id.upozilaTextView) TextView ms_UpozilaTextView;
        @BindView(R.id.unionTextView) TextView ms_UnionTextView;
        @BindView(R.id.wordTextView) TextView ms_WordTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
