/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.helper;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Union_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Upozila_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Word_MS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by RTsoftBD_Siddiqui on 2017-06-06.
 */

public class LoadDropDown{
    Context context;

    public LoadDropDown(final Context context) {
        this.context = context;

        final StringRequest wordRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);
                            int id = object.getInt("unionId");

                            Union_MS union_ms = null;
                            for (int i=0; i< Union_MS.getUnion_msList().size(); i++){
                                if (Union_MS.getUnion_msList().get(i).getId()==id){
                                    union_ms = Union_MS.getUnion_msList().get(i);
                                    break;
                                }
                            }

                            Word_MS word_ms = new Word_MS(object.getInt("id"), object.getString("word"), union_ms);

                            Word_MS.setWord_msList(word_ms);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new MaterialDialog.Builder(context)
                        .content("Something Wrong. Please Restart App and try again.")
                        .show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_WORD);

                return params;
            }
        };


        final StringRequest unionRequest = new StringRequest(Request.Method.GET, ApiUrl._UNION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int listener = 0; listener < array.length(); listener++) {
                        JSONObject object = array.getJSONObject(listener);

                        int id = object.getInt("upozila_id");
                        Upozila_MS upozila_ms = null;

                        for (int i = 0; i<Upozila_MS.getUpozila_msList().size(); i++){
                            if(Upozila_MS.getUpozila_msList().get(i).getId()== id){
                                upozila_ms = Upozila_MS.getUpozila_msList().get(i);
                                break;
                            }
                        }

                        Union_MS union_ms = new Union_MS(object.getInt("id"), object.getString("union_name"), upozila_ms);

                        Union_MS.setUnion_msList(union_ms);
                    }

                    Volley.newRequestQueue(context).add(wordRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        StringRequest upozilaRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);
                            Upozila_MS upozila_ms = new Upozila_MS(object.getInt("id"), object.getString("upozila"));

                            Upozila_MS.setUpozila_msList(upozila_ms);
                        }
                    }

                    Volley.newRequestQueue(context).add(unionRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new MaterialDialog.Builder(context)
                        .content("Something Wrong. Please Restart App and try again.")
                        .show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_UPOZILA);

                return params;
            }
        };
        Volley.newRequestQueue(context).add(upozilaRequest);
    }
}
