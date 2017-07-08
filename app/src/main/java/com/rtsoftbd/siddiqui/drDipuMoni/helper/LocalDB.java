
/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/8/17 3:16 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/8/17 3:01 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rtsoftbd.siddiqui.drDipuMoni.model.AboutSocial;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Resume;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Status;

import java.util.ArrayList;
import java.util.List;


public class LocalDB extends SQLiteOpenHelper {
    private static final String TAG = "LocalDB";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MSDipuMoniDB";

    /*Tables*/
    private static final String TABLE_MANAGE = "manage";
    private static final String TABLE_STATUS = "status";
    private static final String TABLE_POLITICAL_RESUME = "political_resume";
    private static final String TABLE_ACHIEVEMENT = "achievement";

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

    /*Status Keys*/
    private static final String KEY_STATUS_DETAILS = "status_details";
    private static final String KEY_IS_ACTIVE = "is_active";
    private static final String KEY_STATUS_TIME = "statustime";

    /*Political Resume Keys*/
    private static final String KEY_RESUME_TITLE = "title";
    private static final String KEY_RESUME_DETAILS = "details";
    private static final String KEY_RESUME_PLACE = "place";
    private static final String KEY_RESUME_PICTURE_NAME = "picture";

    /*Achievement Keys*/
    private static final String KEY_ACHIEVEMENT_TITLE = "achievement_title";
    private static final String KEY_ACHIEVEMENT_DETAILS = "achievement_details";
    private static final String KEY_ACHIEVEMENT_LOGO = "achievement_logo";

    /*CREATING TABLE*/
    /*QUERY: CREATE MANAGE TABLE*/
    /*###ADD PICTURE TO DB REMAINING######*/
    private static final String CREATE_TABLE_MANAGE = "CREATE TABLE " + TABLE_MANAGE +" ("+
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_SECTION_ABOUT_TITLE + " TEXT,"+
            KEY_SECTION_ABOUT_HEADER + " TEXT,"+
            KEY_SELECTION_ABOUT_DATA + " TEXT,"+
            KEY_SELECTION_ABOUT_LINK + " TEXT,"+
            KEY_SELECTION_ABOUT_PICTURE + " TEXT,"+
            KEY_SOCIAL_LINK1_NAME + " TEXT,"+
            KEY_SOCIAL_LINK1_URL + " TEXT,"+
            KEY_SOCIAL_LINK2_NAME + " TEXT,"+
            KEY_SOCIAL_LINK2_URL + " TEXT,"+
            KEY_SOCIAL_LINK3_NAME + " TEXT,"+
            KEY_SOCIAL_LINK3_URL + " TEXT,"+
            KEY_ADMIN_EMAIL + " TEXT,"+
            KEY_COPYRIGHT + " TEXT )";

    /*QUERY: CREATE STATUS TABLE*/
    private static final String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS + " ("+
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_STATUS_DETAILS + " TEXT,"+
            KEY_IS_ACTIVE + " INTEGER,"+
            KEY_STATUS_TIME + " TEXT )";

    private static final String CREATE_TABLE_POLITICAL_RESUME = "CREATE TABLE " + TABLE_POLITICAL_RESUME + " ("+
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_RESUME_TITLE + " TEXT,"+
            KEY_RESUME_DETAILS + " TEXT,"+
            KEY_RESUME_PLACE + " TEXT,"+
            KEY_RESUME_PICTURE_NAME + " TEXT )";

    private static final String  CREATE_TABLE_ACHIEVEMENT = "CREATE TABLE " + TABLE_ACHIEVEMENT + " ("+
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ACHIEVEMENT_DETAILS +  " TEXT,"+
            KEY_ACHIEVEMENT_TITLE + " TEXT," +
            KEY_ACHIEVEMENT_LOGO + " TEXT )";

    private ContentValues manageContentValues(){
        ContentValues values = new ContentValues();

        values.put(KEY_SECTION_ABOUT_TITLE, AboutSocial.getSectionAboutTitle());
        values.put(KEY_SECTION_ABOUT_HEADER, AboutSocial.getSectorAboutHeader());
        values.put(KEY_SELECTION_ABOUT_DATA, AboutSocial.getSectionAboutData());
        values.put(KEY_SELECTION_ABOUT_LINK, AboutSocial.getSectionAboutLink());
        values.put(KEY_SELECTION_ABOUT_PICTURE, AboutSocial.getSectionAboutPic());
        values.put(KEY_SOCIAL_LINK1_NAME,AboutSocial.getSlink1Name());
        values.put(KEY_SOCIAL_LINK1_URL, AboutSocial.getSlink1Link());
        values.put(KEY_SOCIAL_LINK2_NAME, AboutSocial.getSlink2Name());
        values.put(KEY_SOCIAL_LINK2_URL, AboutSocial.getSlink2Link());
        values.put(KEY_SOCIAL_LINK3_NAME, AboutSocial.getSlink3Name());
        values.put(KEY_SOCIAL_LINK3_URL,AboutSocial.getSlink3Link());
        values.put(KEY_ADMIN_EMAIL, AboutSocial.getAdminEmail());
        values.put(KEY_COPYRIGHT, AboutSocial.getCopyRight());

        return values;
    }

    private ContentValues statusContentValues(Status status){
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS_DETAILS, status.getStatus());
        values.put(KEY_STATUS_TIME, status.get_date());
        values.put(KEY_IS_ACTIVE, status.getIs_active());
        return values;
    }

    private ContentValues resumeAndAchievementContentValues(Resume resume){
        ContentValues values = new ContentValues();
        values.put(KEY_RESUME_DETAILS, resume.getDetails());
        values.put(KEY_RESUME_TITLE, resume.getTitle());
        values.put(KEY_RESUME_PLACE, resume.getPlace());
        values.put(KEY_RESUME_PICTURE_NAME, resume.getPictureString());
        return values;
    }

    public long insertAboutData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_MANAGE, null, manageContentValues());
    }

    public long updateAboutData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_MANAGE, manageContentValues(), KEY_ID + id, null);
    }

    public void getAboutData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryAll(TABLE_MANAGE), null);

        if (c.moveToFirst()){
            do {
                AboutSocial.setSectionAboutTitle(c.getString(c.getColumnIndex(KEY_SECTION_ABOUT_TITLE)));
                AboutSocial.setSectorAboutHeader(c.getString(c.getColumnIndex(KEY_SECTION_ABOUT_HEADER)));
                AboutSocial.setSectionAboutData(c.getString(c.getColumnIndex(KEY_SELECTION_ABOUT_DATA)));
                AboutSocial.setSectionAboutLink(c.getString(c.getColumnIndex(KEY_SELECTION_ABOUT_LINK)));
                AboutSocial.setSectionAboutPic(c.getString(c.getColumnIndex(KEY_SELECTION_ABOUT_PICTURE)));
                AboutSocial.setSlink1Name(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK1_NAME)));
                AboutSocial.setSlink1Link(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK1_URL)));
                AboutSocial.setSlink2Name(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK2_NAME)));
                AboutSocial.setSlink2Link(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK2_URL)));
                AboutSocial.setSlink3Name(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK3_NAME)));
                AboutSocial.setSlink3Link(c.getString(c.getColumnIndex(KEY_SOCIAL_LINK3_URL)));
                AboutSocial.setAdminEmail(c.getString(c.getColumnIndex(KEY_ADMIN_EMAIL)));
                AboutSocial.setCopyRight(c.getString(c.getColumnIndex(KEY_COPYRIGHT)));
            }while (c.moveToNext());
        }

        c.close();
        closeDB();
    }

    public long insertStatus(Status status){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_STATUS, null, statusContentValues(status));
    }

    public long updateStatus(Status status, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.update(TABLE_STATUS, statusContentValues(status), KEY_ID + id, null);
    }

    public List<Status> getAllStatus(){
        List<Status> statuses = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryAll(TABLE_STATUS), null);

        if (c.moveToFirst()){
            do {
                Status status = new Status();
                status.setStatus(c.getString(c.getColumnIndex(KEY_STATUS_DETAILS)));
                status.set_date(c.getString(c.getColumnIndex(KEY_STATUS_TIME)));
                status.setIs_active(c.getInt(c.getColumnIndex(KEY_IS_ACTIVE)));
                status.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                statuses.add(status);
            }while (c.moveToNext());
        }

        c.close();
        closeDB();
        return statuses;
    }

    public long insertPoliticalResume(Resume resume){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_POLITICAL_RESUME, null, resumeAndAchievementContentValues(resume));
    }

    public long updatePoliticalResume(Resume resume, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_POLITICAL_RESUME, resumeAndAchievementContentValues(resume), KEY_ID + id, null);
    }

    public List<Resume> getAllPoliticalResume(){
        List<Resume> resumes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryAll(TABLE_POLITICAL_RESUME), null);

        if (c.moveToFirst()){
            do {
                Resume resume = new Resume();
                resume.setDetails(c.getString(c.getColumnIndex(KEY_RESUME_DETAILS)));
                resume.setTitle(c.getString(c.getColumnIndex(KEY_RESUME_TITLE)));
                resume.setPlace(c.getString(c.getColumnIndex(KEY_RESUME_PLACE)));
                resume.setPictureString(c.getString(c.getColumnIndex(KEY_RESUME_PICTURE_NAME)));
                resumes.add(resume);
            }while (c.moveToNext());
        }

        c.close();
        closeDB();
        return resumes;
    }

    public long insertAchievement(Resume resume){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.insert(TABLE_ACHIEVEMENT, null, resumeAndAchievementContentValues(resume));
    }

    public long updateAchievement (Resume resume, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_ACHIEVEMENT, resumeAndAchievementContentValues(resume), KEY_ID + id, null);
    }

    public List<Resume> getAllAchievement(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Resume> resumes = new ArrayList<>();
        Cursor c = db.rawQuery(queryAll(TABLE_ACHIEVEMENT), null);

        if (c.moveToFirst()){
            do {
                Resume resume = new Resume();
                resume.setTitle(c.getString(c.getColumnIndex(KEY_ACHIEVEMENT_TITLE)));
                resume.setDetails(c.getString(c.getColumnIndex(KEY_ACHIEVEMENT_DETAILS)));
                resume.setPictureString(c.getString(c.getColumnIndex(KEY_ACHIEVEMENT_LOGO)));
                resumes.add(resume);
            }while (c.moveToNext());
        }

        c.close();
        closeDB();
        return resumes;
    }

    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MANAGE);
        db.execSQL(CREATE_TABLE_STATUS);
        db.execSQL(CREATE_TABLE_POLITICAL_RESUME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANAGE);
        onCreate(db);
    }

    public long deleteAll(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, null, null);
    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) db.close();
    }

    private String queryAll(String tableName){
        return "SELECT * FROM " + tableName;
    }
}
