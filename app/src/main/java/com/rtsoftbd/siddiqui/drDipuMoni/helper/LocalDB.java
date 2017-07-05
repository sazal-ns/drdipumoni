
/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/5/17 7:21 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/5/17 7:20 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rtsoftbd.siddiqui.drDipuMoni.model.AboutSocial;


public class LocalDB extends SQLiteOpenHelper {
    private static final String TAG = "LocalDB";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MSDipuMoniDB";

    /*Tables*/
    private static final String TABLE_MANAGE = "manage";

    /*Common Key*/
    private static final String KEY_ID = "_id";

    /*Manage Keys*/
    private static final String KEY_SECTION_ABOUT_TITLE = "section_about_title";
    private static final String KEY_SECTION_ABOUT_HEADER = "sector_about_header";
    private static final String KEY_SELECTION_ABOUT_DATA = "section_about_data";
    private static final String KEY_SELECTION_ABOUT_LINK = "section_about_link";
    private static final String KEY_SELECTION_ABOUT_PICTURE = "section_about_pic";
    private static final String KEY_SOCIAL_LINK1_NAME = "slink1_name";
    private static final String KEY_SOCIAL_LINK1_URL = "slink1_link";
    private static final String KEY_SOCIAL_LINK2_NAME = "slink2_name";
    private static final String KEY_SOCIAL_LINK2_URL = "slink2_link";
    private static final String KEY_SOCIAL_LINK3_NAME = "slink3_name";
    private static final String KEY_SOCIAL_LINK3_URL = "slink3_link";
    private static final String KEY_ADMIN_EMAIL = "adminEmail";
    private static final String KEY_COPYRIGHT = "copy_right";


    /*CREATING TABLE*/
    /*CREATE MANAGE TABLE*/
    /*###ADD PICTURE TO DB REMAINING######*/
    private static final String CREATE_TABLE_MANAGE = "CREATE TABLE " + TABLE_MANAGE +" ("+
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_SECTION_ABOUT_TITLE + " TEXT,"+
            KEY_SECTION_ABOUT_HEADER + " TEXT,"+
            KEY_SELECTION_ABOUT_DATA + " TEXT,"+
            KEY_SELECTION_ABOUT_LINK + " TEXT,"+
            KEY_SOCIAL_LINK1_NAME + " TEXT,"+
            KEY_SOCIAL_LINK1_URL + " TEXT,"+
            KEY_SOCIAL_LINK2_NAME + " TEXT,"+
            KEY_SOCIAL_LINK2_URL + " TEXT,"+
            KEY_SOCIAL_LINK3_NAME + " TEXT,"+
            KEY_SOCIAL_LINK3_URL + " TEXT,"+
            KEY_ADMIN_EMAIL + " TEXT,"+
            KEY_COPYRIGHT + " TEXT )";

    public long insertMange(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SECTION_ABOUT_TITLE, AboutSocial.getSectionAboutTitle());
        values.put(KEY_SECTION_ABOUT_HEADER, AboutSocial.getSectorAboutHeader());
        values.put(KEY_SELECTION_ABOUT_DATA, AboutSocial.getSectionAboutData());
        values.put(KEY_SELECTION_ABOUT_LINK, AboutSocial.getSectionAboutLink());
        values.put(KEY_SOCIAL_LINK1_NAME,AboutSocial.getSlink1Name());
        values.put(KEY_SOCIAL_LINK1_URL, AboutSocial.getSlink1Link());
        values.put(KEY_SOCIAL_LINK2_NAME, AboutSocial.getSlink2Name());
        values.put(KEY_SOCIAL_LINK2_URL, AboutSocial.getSlink2Link());
        values.put(KEY_SOCIAL_LINK3_NAME, AboutSocial.getSlink3Name());
        values.put(KEY_SOCIAL_LINK3_URL,AboutSocial.getSlink3Link());
        values.put(KEY_ADMIN_EMAIL, AboutSocial.getAdminEmail());
        values.put(KEY_COPYRIGHT, AboutSocial.getCopyRight());

        return db.insert(TABLE_MANAGE, null, values);
    }

    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MANAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANAGE);

        onCreate(db);
    }
}
