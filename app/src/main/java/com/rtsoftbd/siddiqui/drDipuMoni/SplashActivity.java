/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/10/17 5:51 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/10/17 4:21 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.LocalDB;
import com.rtsoftbd.siddiqui.drDipuMoni.model.AboutSocial;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

   private LocalDB db = new LocalDB(SplashActivity.this);

    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";
    @BindView(R.id.progressBar) ProgressBar ms_ProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        addShortCut();

        aboutDate date = new aboutDate();
        date.execute();
    }

    private class aboutDate extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        db.deleteAll(LocalDB.TABLE_ACHIEVEMENT);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("0");

                        DownImageTask downImageTask = new DownImageTask();
                        downImageTask.execute(ApiUrl.ASSETS_UPLOAD + object.getString("section_about_pic"));

                        AboutSocial.setSectorAboutHeader(object.getString("sector_about_header").toUpperCase());
                        AboutSocial.setSectionAboutData(object.getString("section_about_data"));
                        AboutSocial.setSectionAboutTitle(object.getString("section_about_title"));
                        AboutSocial.setSectionAboutLink(object.getString("section_about_link"));
                        AboutSocial.setSlink1Name(object.getString("slink1_name"));
                        AboutSocial.setSlink1Link(object.getString("slink1_link"));
                        AboutSocial.setSlink2Name(object.getString("slink2_name"));
                        AboutSocial.setSlink2Link(object.getString("slink2_link"));
                        AboutSocial.setSlink3Name(object.getString("slink3_name"));
                        AboutSocial.setSlink3Link(object.getString("slink3_link"));
                        AboutSocial.setCopyRight(object.getString("copy_right"));
                        AboutSocial.setAdminEmail(object.getString("adminEmail"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.toString().contains("NoConnectionError")) {
                        db.getAboutData();
                        logo = BitmapFactory.decodeByteArray(AboutSocial.getImage(),0,AboutSocial.getImage().length);

                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }else new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("Error")
                            .setMessage("Something wrong. Please try after sometime :(")
                            .show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_ABOUT_SOCIAL);

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(request,"S");

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
        }
    }

    public static Bitmap logo = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_face_white_48dp);
    public boolean is = false;

    public static Bitmap getLogo() {
        return logo;
    }

    public class DownImageTask extends AsyncTask<String, Void, Bitmap> {


        public boolean is() {
            return is;
        }

        public DownImageTask() {
            //this.imageView = imageView;
        }


        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            logo = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_face_white_48dp);
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
                 new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Error")
                        .setMessage("Something wrong. Please try after sometime :(")
                        .show();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            //imageView.setImageBitmap(result);
            is = true;

            AboutSocial.setImage(convertImage(logo));
            db.insertAboutData();

            AppController.getInstance().cancelPendingRequests("S");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            Log.e("done", String.valueOf(is));
        }
    }

    private static byte[] convertImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void addShortCut() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded) return;

        Intent shortCut = new Intent(getApplicationContext(), SplashActivity.class);
        shortCut.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCut);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.icon));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);
        getApplicationContext().sendBroadcast(addIntent);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.apply();

    }

}
