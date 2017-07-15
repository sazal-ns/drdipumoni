/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 * (C) 7/15/17 4:38 PM
 *  www.fb.com/sazal.ns
 *  _______________________________________
 *    Name:     DipuMoni
 *    Updated at: 7/15/17 3:17 PM
 *  ________________________________________
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.Config;
import com.rtsoftbd.siddiqui.drDipuMoni.model.AboutSocial;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_NEWS_FEED = "NEWS FEED";
    private static final String TAG_ABOUT = "ABOUT";
    private static final String TAG_LATEST_UPDATE = "LATEST UPDATE";
    private static final String TAG_DEV_WORK ="DEV WORK";
    private static final String TAG_ACHIEVEMENT = "ACHIEVEMENT";
    private static final String TAG_RESUME = "RESUME";
    private static final String TAG_EDUCATION = "EDUCATION";
    private static final String TAG_GALLERY = "GALLERY";
    private static final String TAG_SOCIAL = "SOCIAL";
    private static final String TAG_REG = "REGISTRATION";
    private static final String TAG_LOGIN = "LOGIN";

    private static String TAG_CURRENT = TAG_NEWS_FEED;

    @BindView(R.id.toolbar) Toolbar ms_Toolbar;
    @BindView(R.id.nav_view) NavigationView ms_NavView;
    @BindView(R.id.drawer_layout) DrawerLayout ms_DrawerLayout;

    private ActionBarDrawerToggle toggle;

    public static int navItemIndex = 0;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private boolean isLogin;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ms_Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ms_Toolbar);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        mHandler = new Handler();

        ms_DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, ms_DrawerLayout, ms_Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ms_DrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        activityTitles = getResources().getStringArray(R.array.nav_array);
        ms_NavView = (NavigationView) findViewById(R.id.nav_view);

        View header = ms_NavView.getHeaderView(0);
        CircleImageView pic = (CircleImageView) header.findViewById(R.id.imageView);
        pic.setImageBitmap(SplashActivity.getLogo());

        TextView textView = (TextView) header.findViewById(R.id.nameTextView);
        textView.setText(AboutSocial.getSectorAboutHeader());


        SharedPreferences preferences = getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        if(preferences.getBoolean("login",false)) {
            setNavigationClickMessage();
            isLogin = true;
            navItemIndex =9;
            TAG_CURRENT = TAG_LOGIN;
            loadHomeFragment();
        }else {
            setNavigationClickHandler();
            if (savedInstanceState == null) {
                navItemIndex = 0;
                TAG_CURRENT = TAG_NEWS_FEED;
                loadHomeFragment();
            } else {
                ms_NavView.getMenu().getItem(navItemIndex).setChecked(true);
                getSupportActionBar().setTitle(activityTitles[navItemIndex]);
            }

            if (getIntent().getBooleanExtra("Exit", false)) {
                finish();
            }
        }

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    private void setNavigationClickMessage() {
        ms_NavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_news_feed:
                        a();
                        break;
                    case R.id.nav_about:
                        a();
                        break;
                    case R.id.nav_latest_update:
                        a();
                        break;
                    case R.id.nav_dev:
                        a();
                        break;
                    case R.id.nav_achievement:
                        a();
                        break;
                    case R.id.nav_resume:
                        a();
                        break;
                    case R.id.nav_education:
                        a();
                        break;
                    case R.id.nav_gallery:
                        a();
                        break;
                    case R.id.nav_social:
                        a();
                        break;
                    case R.id.nav_reg:
                        a();
                        break;
                    case R.id.nav_login:
                        a();
                        break;
                    default:
                        navItemIndex = 9;
                }
                navItemIndex = 9;
                loadHomeFragment();
                return true;
            }
        });
    }

    void a() {
        SharedPreferences preferences = getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        if(preferences.getBoolean("login",false)) {
            new MaterialDialog.Builder(this)
                    .content("Logout to go back to User View")
                    .positiveText("LogOut")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferences.Editor editor = getSharedPreferences("LOGIN_MS",Context.MODE_PRIVATE).edit();
                            editor.putBoolean("login", false);
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .negativeText("No")
                    .cancelable(false)
                    .show();

        }
    }

    private void setNavigationClickHandler() {
        ms_NavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_news_feed:
                        navItemIndex = 0;
                        TAG_CURRENT = TAG_NEWS_FEED;
                        break;
                    case R.id.nav_about:
                        navItemIndex = 1;
                        TAG_CURRENT = TAG_ABOUT;
                        break;
                    case R.id.nav_latest_update:
                        navItemIndex = 2;
                        TAG_CURRENT = TAG_LATEST_UPDATE;
                        break;
                    case R.id.nav_dev:
                        navItemIndex = 3;
                        TAG_CURRENT = TAG_DEV_WORK;
                        break;
                    case R.id.nav_achievement:
                        navItemIndex = 4;
                        TAG_CURRENT = TAG_ACHIEVEMENT;
                        break;
                    case R.id.nav_resume:
                        navItemIndex = 5;
                        TAG_CURRENT = TAG_RESUME;
                        break;
                    case R.id.nav_education:
                        navItemIndex = 6;
                        TAG_CURRENT = TAG_EDUCATION;
                        break;
                    case R.id.nav_gallery:
                        navItemIndex = 7;
                        TAG_CURRENT = TAG_GALLERY;
                        break;
                    case R.id.nav_social:
                        navItemIndex = 8;
                        TAG_CURRENT = TAG_SOCIAL;
                        break;
                    case R.id.nav_reg:
                        navItemIndex = 9;
                        TAG_CURRENT = TAG_REG;
                        break;
                    case R.id.nav_login:
                        navItemIndex = 10;
                        TAG_CURRENT = TAG_LOGIN;
                        break;
                    default:
                        navItemIndex = 0;
                }

                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, ms_DrawerLayout, ms_Toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        ms_DrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {

        ms_NavView.getMenu().getItem(navItemIndex).setChecked(true);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frame,fragment,TAG_CURRENT);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        mHandler.post(runnable);

        ms_DrawerLayout.closeDrawers();

        invalidateOptionsMenu();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",activityTitles[navItemIndex]);
    }

    @Override
    public void onBackPressed() {

        SharedPreferences preferences = getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        if(preferences.getBoolean("login",false)) {
            new MaterialDialog.Builder(this)
                    .content("Logout to go back to User View")
                    .positiveText("LogOut")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferences.Editor editor = getSharedPreferences("LOGIN_MS",Context.MODE_PRIVATE).edit();
                            editor.putBoolean("login", false);
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .negativeText("No")
                    .cancelable(false)
                    .show();

        }else {

            if (ms_DrawerLayout.isDrawerOpen(GravityCompat.START)) {
                ms_DrawerLayout.closeDrawers();
                return;
            }

            if (shouldLoadHomeFragOnBackPress) {
                if (navItemIndex != 0) {
                    navItemIndex = 0;
                    TAG_CURRENT = TAG_NEWS_FEED;
                    loadHomeFragment();
                    return;
                }


                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.exit))
                        .setMessage(getResources().getString(R.string.sure))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Exit", true);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
            menu.removeItem(R.id.action_sms);
            menu.removeItem(R.id.action_status);
            menu.removeItem(R.id.action_add_member);
            menu.removeItem(R.id.action_edit_status);
            menu.removeItem(R.id.action_email);
            menu.removeItem(R.id.action_logout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
              new MaterialDialog.Builder(this)
                        .customView(R.layout.about_us, true)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex){
            case 1:
                return new AboutFragment();
            case 2:
                return new LatestUpdateFragment();
            case 3:
                return new DevWorkFragment();
            case 4:
                return new AchivementFragment();
            case 5:
                return new ResumeFragment();
            case 6:
                return new EducationFragment();
            case 7:
                return new GalleryFragment();
            case 8:
                return new SocialFragment();
            case 9:
                return new RegFragment();
            case 10:
                return new LoginFragment();
            default:
                return new StatusFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
