/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.rtsoftbd.siddiqui.drDipuMoni.model.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.statusList)
    ListView ms_StatusList;
    Unbinder unbinder;

    private StatusAdapter statusAdapter;
    private List<Status> statusList = new ArrayList<>();
    private Boolean isLogin;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
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
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        unbinder = ButterKnife.bind(this, view);

        final SharedPreferences preferences = getContext().getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        isLogin = preferences.getBoolean("login",false);

        statusAdapter = new StatusAdapter(statusList, getActivity(),1);
        ms_StatusList.setAdapter(statusAdapter);

        loadStatus();

        ms_StatusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.UPDATE_STATUS_URL, new Response.Listener<String>() {
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
                                                params.put(ApiUrl.KEY_STATUS_UPDATE_ID, String.valueOf(status.getId()));
                                                params.put(ApiUrl.KEY_STATUS_DATA, input.toString());

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
                                        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.DELETE_STATUS_URL, new Response.Listener<String>() {
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

                                                params.put(ApiUrl.KEY_STATUS_ID,String.valueOf(status.getId()));

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
        return view;
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
                            status.setId(object.getInt("statusid"));
                            status.setIs_active(object.getInt("is_active"));
                            status.setStatus(object.getString("status_details"));
                            status.set_date(object.getString("statustime"));

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
                params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_STATUS);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
