/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.drDipuMoni.customeAdapter.StatusAdapter;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.model.DevUnion;
import com.rtsoftbd.siddiqui.drDipuMoni.model.DevUpozila;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Status;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Union_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Upozila_MS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DevWorkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DevWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DevWorkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private StatusAdapter statusAdapter;
    private List<Status> statusList = new ArrayList<>();
    private Boolean isLogin;
    private Spinner unionSpinner, upozilaSpinner;
    private List<DevUpozila> devUpozilaList = new ArrayList<>();
    private List<DevUnion> devUnionList = new ArrayList<>();

    ArrayAdapter<DevUpozila> upozilaAdapter;
    ArrayAdapter<DevUnion> unionArrayAdapter;

    private boolean isSpinnerTouched = false;
    private boolean isSpinnerTouched2 = false;

    public DevWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DevWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DevWorkFragment newInstance(String param1, String param2) {
        DevWorkFragment fragment = new DevWorkFragment();
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
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_dev_work, container, false);
        final ListView ms_list = (ListView) v.findViewById(R.id.devList);
        unionSpinner = (Spinner) v.findViewById(R.id.unionSpinnerDev);
        upozilaSpinner= (Spinner) v.findViewById(R.id.upozilaSpinnerDev);


        upozilaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, devUpozilaList);
        upozilaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upozilaSpinner.setAdapter(upozilaAdapter);

        unionArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, devUnionList);
        unionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unionSpinner.setAdapter(unionArrayAdapter);

        statusAdapter = new StatusAdapter(statusList, getActivity(),2);
        ms_list.setAdapter(statusAdapter);

        final SharedPreferences preferences = getContext().getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        isLogin = preferences.getBoolean("login",false);

        loadStatus();

        loadUpozila();

        upozilaSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });

        upozilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DevUpozila upo = (DevUpozila) parent.getItemAtPosition(position);
                if (isSpinnerTouched) {
                    List<Status> statuses = new ArrayList<>();
                    for (int i = 0; i < statusList.size(); i++) {
                        if (upo.getUpozilaID() == statusList.get(i).getUpzila_id()) {
                            statuses.add(statusList.get(i));
                        }
                    }
                    if (upo.getUpozilaID()==0) statuses.addAll(statusList);
                    statusAdapter = new StatusAdapter(statuses, getActivity(),2);
                    ms_list.setAdapter(statusAdapter);
                    statusAdapter.notifyDataSetChanged();
                }
                    unionArrayAdapter.clear();
                List<DevUnion> uni = new ArrayList<>();
                    for (DevUnion union_ms1 : devUnionList) {
                        if (union_ms1.getDevUpozila().getUpozilaID() == upo.getUpozilaID()) {
                            uni.add(union_ms1);
                        }
                    }

                unionArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, uni);
                unionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                unionSpinner.setAdapter(unionArrayAdapter);
                unionArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DevUnion upo = (DevUnion) parent.getItemAtPosition(position);
                if (isSpinnerTouched) {
                    List<Status> statuses = new ArrayList<>();
                    for (int i = 0; i < statusList.size(); i++) {
                        if (upo.getUnionID() == statusList.get(i).getUnion_id()) {
                            statuses.add(statusList.get(i));
                        }
                    }
                    if (upo.getUnionID()==0) statuses.addAll(statusList);
                    statusAdapter = new StatusAdapter(statuses, getActivity(),2);
                    ms_list.setAdapter(statusAdapter);
                    statusAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ms_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Status status = (Status) parent.getItemAtPosition(position);



                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflater.inflate(R.layout.status_details, null);
                AppCompatTextView statusTextView = (AppCompatTextView) v.findViewById(R.id.statusDetails);
                AppCompatTextView dateTextView = (AppCompatTextView) v.findViewById(R.id.dateView);
                FloatingActionButton editFab = (FloatingActionButton) v.findViewById(R.id.editFabStatus);
                FloatingActionButton deleteFab = (FloatingActionButton) v.findViewById(R.id.deleteFabStatus);

                statusTextView.setText(status.getStatus());
                dateTextView.setText(status.get_date());

                final MaterialDialog d =  new MaterialDialog.Builder(getActivity())
                        .customView(v, true)
                        .show();

                if (!isLogin) {
                    editFab.setVisibility(View.GONE);
                    deleteFab.setVisibility(View.GONE);
                }

                editFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .title("Update Status")
                                .input(null, status.getStatus(), false, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Updating.....");
                                        progressDialog.show();
                                        StringRequest request = new StringRequest(Request.Method.POST, "http://drdipumoni.tk/Mobile_api/update_development_work", new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                statusAdapter.notifyDataSetChanged();
                                                statusList.get(position).setStatus(input.toString());
                                                progressDialog.dismiss();
                                                d.dismiss();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }){

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String , String > params = new HashMap<>();
                                                params.put("id", String.valueOf(status.getId()));
                                                params.put("develop_work_title", input.toString());

                                                return params;
                                            }
                                        };

                                        AppController.getInstance().addToRequestQueue(request);
                                    }
                                })
                                .show();

                    }
                });

                deleteFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .title("Sure?")
                                .content("Are you sure to delete this status?")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        StringRequest request = new StringRequest(Request.Method.POST, "http://drdipumoni.tk/Mobile_api/delete_development_work", new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                statusList.remove(position);
                                                statusAdapter.notifyDataSetChanged();
                                                d.dismiss();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();

                                                params.put("id",String.valueOf(status.getId()));

                                                return params;
                                            }
                                        };

                                        AppController.getInstance().addToRequestQueue(request);
                                    }
                                })
                                .positiveColor(getContext().getResources().getColor(android.R.color.holo_red_dark))
                                .positiveText("YES")
                                .negativeText("NO")
                                .negativeColor(getContext().getResources().getColor(android.R.color.holo_green_dark))
                                .autoDismiss(true)
                                .show();
                    }
                });
            }
        });

        return v;
    }

    DevUpozila u = new DevUpozila();
    private void loadUpozila() {
            StringRequest request1 = new StringRequest(Request.Method.POST, "http://drdipumoni.tk/api/backup.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);


                        u.setUpozilaID(0);
                        u.setUpozilaName("Select Upozila");

                        devUpozilaList.add(u);

                        Iterator keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String dynamicKey = (String) keys.next();

                            if (!dynamicKey.contains("error")) {
                                JSONObject object = jsonObject.getJSONObject(dynamicKey);

                                DevUpozila upozila_ms = new DevUpozila();
                                upozila_ms.setUpozilaID(object.getInt("develop_work_upozila_id"));
                                upozila_ms.setUpozilaName(object.getString("upozila_name"));

                                devUpozilaList.add(upozila_ms);
                            }
                        }

                        upozilaAdapter.notifyDataSetChanged();
                        loadUnion();
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
                    params.put(ApiUrl.KEY_DATA_REQUEST, "develop_work_upozila");

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(request1, "S");
    }

    private void loadUnion() {
        StringRequest request1 = new StringRequest(Request.Method.POST, "http://drdipumoni.tk/api/backup.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    DevUnion un =new DevUnion();
                    un.setUnionID(0);
                    un.setUnionName("Select Union");
                    un.setDevUpozila(u);
                    devUnionList.add(un);

                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);

                            DevUnion union =new DevUnion();
                            union.setUnionID(object.getInt("develop_work_union_id"));
                            union.setUnionName(object.getString("union_name"));
                            int id = object.getInt("develop_work_upozila_id");

                            DevUpozila upozila = new DevUpozila();
                            for (int i=0; i< devUpozilaList.size(); i++){
                                if (devUpozilaList.get(i).getUpozilaID()==id){
                                    upozila = devUpozilaList.get(i);
                                    break;
                                }
                            }

                            union.setDevUpozila(upozila);

                            devUnionList.add(union);
                        }
                    }

                    unionArrayAdapter.notifyDataSetChanged();
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
                params.put(ApiUrl.KEY_DATA_REQUEST, "develop_work_union");

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request1, "S");
    }

    private void loadStatus() {
        StringRequest request1 = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);

                            Status status = new Status();
                            status.setId(object.getInt("develop_work_id"));
                            status.setIs_active(100);
                            status.setStatus(object.getString("develop_work_title"));
                            status.set_date(object.getString("develop_work_details"));
                            status.setUnion_id(object.getInt("develop_work_union_id"));
                            status.setUpzila_id(object.getInt("develop_work_upozila_id"));

                            statusList.add(status);
                        }
                    }
                    statusAdapter.notifyDataSetChanged();
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
                params.put(ApiUrl.KEY_DATA_REQUEST, "develop_work");

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request1, "S");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
