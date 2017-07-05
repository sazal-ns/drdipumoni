
/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 *  www.fb.com/sazal.ns
 *  ------------------------------------
 *    Name:     DipuMoni
 *    Created at:  7/5/17 5:14 PM
 *    Updated at: 7/5/17 5:14 PM
 *  ------------------------------------
 */

package com.rtsoftbd.siddiqui.drDipuMoni.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    private static final String KEYS_SELECTION_ABOUT_LINK = "section_about_link";




    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
