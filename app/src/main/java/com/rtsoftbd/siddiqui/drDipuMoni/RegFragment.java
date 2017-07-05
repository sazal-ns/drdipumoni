/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AndroidMultiPartEntity;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.LoadDropDown;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Union_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Upozila_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Word_MS;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.nameEditText)
    EditText ms_NameEditText;
    @BindView(R.id.phoneEditText)
    EditText ms_PhoneEditText;
    @BindView(R.id.emailEditText)
    EditText ms_EmailEditText;
    @BindView(R.id.maleRadioButton)
    RadioButton ms_MaleRadioButton;
    @BindView(R.id.femaleRadioButton)
    RadioButton ms_FemaleRadioButton;
    @BindView(R.id.upozilaSpinner)
    Spinner ms_UpozilaSpinner;
    @BindView(R.id.unionSpinner)
    Spinner ms_UnionSpinner;
    @BindView(R.id.wordSpinner)
    Spinner ms_WordSpinner;
    @BindView(R.id.submitButton)
    AppCompatButton ms_SubmitButton;
    @BindView(R.id.nidEditText)
    EditText ms_NidEditText;
    @BindView(R.id.designationSpinner)
    Spinner ms_DesignationSpinner;
    @BindView(R.id.imageButton)
    AppCompatImageButton ms_uploadButton;
    @BindView(R.id.image)
    AppCompatImageView ms_image;

    private String name, phone, email, gander, upozila, union, word, nid, deg;

    private List<String> degs = new ArrayList<>();

    private ArrayAdapter<String> spinnerAdapter;

    List<Upozila_MS> upozila_msList = new ArrayList<>();
    List<Union_MS> union_msList = new ArrayList<>();
    List<Word_MS> word_msList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sp;
    private static final String SP = "ms";

    private long totalSize = 0;
    private ProgressDialog progressDialog;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CALL_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    String path, imagePath;
    private OnFragmentInteractionListener mListener;

    public RegFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegFragment newInstance(String param1, String param2) {
        RegFragment fragment = new RegFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new LoadDropDown(getContext());

        sp = getContext().getSharedPreferences(SP, MODE_PRIVATE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext());
        ms_PhoneEditText.setText("01");

        loadSpinners(ApiUrl.TABLE_DESIGNATION);

        upozila_msList = Upozila_MS.getUpozila_msList();
        ArrayAdapter<Upozila_MS> upozilaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, upozila_msList);
        upozilaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_UpozilaSpinner.setAdapter(upozilaAdapter);
        upozilaAdapter.clear();
        final Upozila_MS upozila_ms = new Upozila_MS(0, "Send All");
        upozila_msList.add(upozila_ms);
        upozilaAdapter.notifyDataSetChanged();

        //union_msList = Union_MS.getUnion_msList();
        final Union_MS union_ms = new Union_MS(0, "Send All", upozila_ms);
        union_msList.add(union_ms);
        final ArrayAdapter<Union_MS> union_msArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, union_msList);
        union_msArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_UnionSpinner.setAdapter(union_msArrayAdapter);

        // word_msList = Word_MS.getWord_msList();
        final ArrayAdapter<Word_MS> word_msArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, word_msList);
        word_msArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_WordSpinner.setAdapter(word_msArrayAdapter);
        word_msArrayAdapter.clear();
        final Word_MS word_ms = new Word_MS(0, "Send All", union_ms);
        word_msList.add(word_ms);
        word_msArrayAdapter.notifyDataSetChanged();

        ms_DesignationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deg = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ms_UpozilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Upozila_MS upozila_ms1 = (Upozila_MS) parent.getItemAtPosition(position);
                union_msArrayAdapter.clear();
                upozila = upozila_ms1.getUpozila_name();
                union_msList.add(union_ms);
                for (Union_MS union_ms1 : Union_MS.getUnion_msList()) {
                    if (union_ms1.getUpozila_ms().getId() == upozila_ms1.getId()) {
                        union_msList.add(union_ms1);
                    }
                }
                union_msArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ms_UnionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Union_MS union_ms1 = (Union_MS) parent.getItemAtPosition(position);
                word_msArrayAdapter.clear();
                union = union_ms1.getUnion_name();
                word_msList.add(word_ms);
                for (Word_MS wordMs : Word_MS.getWord_msList()) {
                    if (wordMs.getUnion_ms().getId() == union_ms1.getId()) {
                        word_msList.add(wordMs);
                    }
                }
                word_msArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ms_WordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Word_MS word_ms1 = (Word_MS) parent.getItemAtPosition(position);
                word = word_ms1.getWord_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ms_SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickData();
                submitData();
            }
        });

        ms_uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        getPermitions();

        saveImage(getBitmapFromAsset("user.png"), "user.png");

        return view;
    }

    private Bitmap getBitmapFromAsset(String strName) {
        AssetManager assetManager = getContext().getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            if (istr.equals(null)) {
                bitmap = BitmapFactory.decodeStream(assetManager.open("user.png"));
            } else {
                bitmap = BitmapFactory.decodeStream(istr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    private void saveImage(Bitmap finalBitmap, String imageName) {
        File sdCard = Environment.getExternalStorageDirectory();
        File myDir = new File(sdCard.getAbsolutePath() +"/"+ getContext().getPackageName());
        path = myDir.getPath();
        myDir.mkdirs();

        File file = new File(myDir, imageName);
        if (file.exists()) {
            Log.i("file exists", "" + imageName);
            file.delete();
        } else {
            Log.i("file does not exists", "" + imageName);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
            imagePath = path.concat("/"+imageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPermitions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[1])){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Storage access Permission");
                builder.setMessage("To save your profile picture, this app needs write storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(getActivity(),permissionsRequired,CALL_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }else if (sp.getBoolean(permissionsRequired[0],false)){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Storage access Permission");
                builder.setMessage("To save your profile picture, this app needs write storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        getActivity().startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getActivity().getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }else {
                ActivityCompat.requestPermissions(getActivity(), permissionsRequired,
                        CALL_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.apply();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_CONSTANT){

            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }


            if (allgranted){
                //saveImage(bitmap, User.getImageName());
            }else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[1])){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Storage access Permission");
                    builder.setMessage("To save your profile picture, this app needs write storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(getActivity(), permissionsRequired,
                                    CALL_PERMISSION_CONSTANT);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else new MaterialDialog.Builder(getActivity())
                        .title("Sorry")
                        .content("Can't Save picture so profile update is not possible.")
                        .show();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sentToSettings){
            if ((ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) == PackageManager.PERMISSION_GRANTED) ) {
                //saveImage(bitmap, User.getImageName());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION_SETTING){
            if ((ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) == PackageManager.PERMISSION_GRANTED)){
                //saveImage(bitmap, User.getImageName());
            }
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

            File sourceFile = new File(imagePath);
            double fileSize = (sourceFile.length() / 1024.0) / 1024.0;
            if (fileSize >= 2.0) {
                new MaterialDialog.Builder(getContext())
                        .cancelable(true)
                        .content("File is not learger the 2 MB. Image size is " +String.valueOf(fileSize))
                        .show();

                return;
            }

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ms_image.setImageBitmap(bmp);

        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void loadSpinners(final String key) {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);

                                degs.add(object.getString("designation_name"));
                        }
                    }


                        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, degs);
                        ms_DesignationSpinner.setAdapter(spinnerAdapter);

                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                if (error.toString().contains("NoConnectionError")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("No Active Internet Connection :(")
                            .show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ApiUrl.KEY_DATA_REQUEST, key);

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }


    private void submitData() {
        if (!validate()) {
            onValidationFailed();
            return;
        }

        new UploadFileToServer().execute();

        /*StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.INSERT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("false"))
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.replay))
                            .setMessage(getResources().getString(R.string.trueAns))
                            .show();
                else
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.replay))
                            .setMessage(getResources().getString(R.string.falseAnd))
                            .show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ApiUrl.KEY_STORE_REQUEST, ApiUrl.TABLE_VOTER_INFO);
                params.put(ApiUrl.KEY_USER_NAME, name);
                params.put(ApiUrl.KEY_PHONE, phone);
                params.put(ApiUrl.KEY_EMAIL, email);
                params.put(ApiUrl.KEY_GENDER, gander);
                params.put(ApiUrl.KEY_UPOZILA, upozila);
                params.put(ApiUrl.KEY_UNION, union);
                params.put(ApiUrl.KEY_WORD, word);
                params.put(ApiUrl.KEY_TYPE, deg);
                params.put(ApiUrl.KEY_NID, nid);

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);*/

    }

    private void onValidationFailed() {

    }

    private boolean validate() {
        boolean ok = true;

        if (name.isEmpty() || name.length() > 256) {
            ms_NameEditText.setError(getResources().getString(R.string.nameError));
            ok = false;
        } else ms_NameEditText.setError(null);

        if (phone.isEmpty() || phone.length() != 11) {
            ms_PhoneEditText.setError(getResources().getString(R.string.phoneError));
            ok = false;
        } else ms_PhoneEditText.setError(null);


        if (gander.isEmpty()) {
            ms_MaleRadioButton.setBackgroundColor(getResources().getColor(R.color.material_red_900));
            ms_FemaleRadioButton.setBackgroundColor(getResources().getColor(R.color.material_red_900));
            ok = false;
        } else {
            ms_MaleRadioButton.setBackgroundColor(getResources().getColor(android.R.color.white));
            ms_FemaleRadioButton.setBackgroundColor(getResources().getColor(android.R.color.white));
        }

        return ok;
    }


    private void pickData() {
        name = ms_NameEditText.getText().toString().trim();
        phone = ms_PhoneEditText.getText().toString().trim();
        email = ms_EmailEditText.getText().toString().trim();
        nid = ms_NidEditText.getText().toString().trim();
        if (ms_MaleRadioButton.isChecked()) gander = "male";
        else if (ms_FemaleRadioButton.isChecked()) gander = "female";
        else gander = "";
    }


    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/


    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //progressBar.setProgress(0);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            // progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            // progressBar.setProgress(progress[0]);

            // updating percentage value
            // txtPercentage.setText(String.valueOf(progress[0]) + "%");
            progressDialog.setMessage("Uploading & Sing Up . . . "+ progress[0] );
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ApiUrl.URL_ADD);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(imagePath);
                // Adding file data to http body
                entity.addPart(ApiUrl.KEY_USER_FILE, new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart(ApiUrl.KEY_TYPE, new StringBody(deg));
                entity.addPart(ApiUrl.KEY_WORD, new StringBody(word));
                entity.addPart(ApiUrl.KEY_UNION, new StringBody(union));
                entity.addPart(ApiUrl.KEY_UPOZILA, new StringBody(upozila));
                entity.addPart(ApiUrl.KEY_EMAIL, new StringBody(email));
                entity.addPart(ApiUrl.KEY_NID, new StringBody(String.valueOf(nid)));
                entity.addPart(ApiUrl.KEY_PHONE, new StringBody(String.valueOf(phone)));
                entity.addPart(ApiUrl.KEY_GENDER, new StringBody(gander));
                entity.addPart(ApiUrl.KEY_PHONE, new StringBody(phone));
                entity.addPart(ApiUrl.KEY_ADDRESS, new StringBody("NO ADDRESS"));
                entity.addPart(ApiUrl.KEY_USER_NAME, new StringBody(name));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("HELLO", "Response from server: " + result);

            if (result.contains("Data Successfully Sent")){
                new MaterialDialog.Builder(getContext())
                        .title("JOB DONE")
                        .show();
            }else new MaterialDialog.Builder(getContext())
                    .title("ERROR")
                    .show();

            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @OnClick({R.id.maleRadioButton, R.id.femaleRadioButton})
    public void onRadioButtonClicked(View view) {

        ms_MaleRadioButton.setBackgroundColor(getResources().getColor(android.R.color.white));
        ms_FemaleRadioButton.setBackgroundColor(getResources().getColor(android.R.color.white));
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
