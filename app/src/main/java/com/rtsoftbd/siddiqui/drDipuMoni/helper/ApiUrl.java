/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni.helper;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-13.
 */

public final class ApiUrl {

    private static final String URL= "http://drdipumoni.tk/api/";

    private static final String URL_2 = "http://drdipumoni.tk/Mobile_api/";

    public static final String BASE_URL = URL.concat("backup.php");

    public static final String INSERT_URL = URL.concat("ins.php");

    public static final String DELETE_URL = URL.concat("delete.php");

    public static final String STATUS_URL = URL_2.concat("submit_status");

    public static final String DELETE_STATUS_URL = URL_2.concat("delete_status");

    public static final String UPDATE_STATUS_URL = URL_2.concat("update_status");

    public static final String KEY_STATUS_ID = "status_id";

    public static final String KEY_USER_STATUS_ID = "userid";

    public static final String KEY_STATUS_DATA = "status_data";

    public static final String KEY_STATUS_UPDATE_ID = "statusid";

    public static final String KEY_STATUS_DATE_ = "statustime";

    public static final String _UNION = URL_2.concat("unionbag");

    public static final String SEND_SMS = "http://drdipumoni.tk/Mobile_api/checksms";

    public static final String SEND_EMAIL = URL_2.concat("checkemail");

    public static final String EMAIL_SUBJECT = "Subject";

    public static final String EMAIL_DES = "description";

    public static final String KEY_SMS_BODY = "description";

    public static final String KEY_SMS_UPOZILA = "upozila";

    public static final String KEY_SMS_UNION = "union_checksms";

    public static final String KEY_SMS_WORD = "word_checksms";

    public static final String URL_ADD = "http://drdipumoni.tk/Mobile_api/add_voter";

    public static final String KEY_USER_FILE = "userfile";


    public static final String TABLE_STATUS = "status";

    public static final String TABLE_USER = "users";

    public static final String TABLE_ACHIVEMENT = "achivement";

    public static final String TABLE_EDUCATION = "education";

    public static final String TABLE_LATEST_UPDATE = "latest_update";

    public static final String TABLE_GALLERY = "gallery";

    public static final String TABLE_RESUME = "political_resume";

    public static final String TABLE_WORD = "word";

    public static final String TABLE_UPOZILA = "upozila";

    public static final String TABLE_DESIGNATION = "designation";

    public static final String TABLE_VOTER_INFO = "voter_info";

    public static final String TABLE_ABOUT_SOCIAL = "manage";


    public static final String KEY_DATA_REQUEST = "data_request";

    public static final String KEY_STORE_REQUEST = "store_request";

    public static final String KEY_DELETE_REQUEST = "delete_request";

    public static final String KEY_USER_NAME = "User_Name";

    public static final String KEY_PHONE = "phone";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_GENDER = "gender";

    public static final String KEY_UPOZILA = "upozila";

    public static final String KEY_UNION = "upo_union";

    public static  final String KEY_WORD = "word";

    public static final  String KEY_TYPE = "designation";

    public static final String KEY_NID = "national_Id";

    public static final String KEY_ADDRESS = "address";



    private static final String ASSETS_PATH = "http://drdipumoni.tk/assets/";

    public  static final String ASSETS_UPLOAD = ASSETS_PATH.concat("uploads/");

    public static final String  ASSETS_GALLERY = ASSETS_UPLOAD.concat("gallery/");

    public static final String ASSETS_ACHIVEMENT = ASSETS_UPLOAD.concat("achievements/");

    public static final String ASSETS_LATEST_UPDATE = ASSETS_UPLOAD.concat("latestupdate/");

    public static final String ASSETS_RESUME = ASSETS_UPLOAD.concat("political_resume/");


}