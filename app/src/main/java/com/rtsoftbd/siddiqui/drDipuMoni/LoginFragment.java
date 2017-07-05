/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.userNameAppCompatEditText)
    AppCompatEditText ms_UserNameAppCompatEditText;
    @BindView(R.id.passwordAppCompatEditText)
    AppCompatEditText ms_PasswordAppCompatEditText;
    @BindView(R.id.loginAppCompatButton)
    AppCompatButton ms_LoginAppCompatButton;
    Unbinder unbinder;

    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

        SharedPreferences preferences = getContext().getSharedPreferences("LOGIN_MS", Context.MODE_PRIVATE);
        if(preferences.getBoolean("login",false)) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AdminFragment llf = new AdminFragment();
            ft.replace(R.id.frame, llf);
            ft.commit();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("accessing admin panel . . . . ");
        progressDialog.setCancelable(false);

        return view;
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

    @OnClick(R.id.loginAppCompatButton)
    public void onViewClicked() {
        ObjectAnimator.ofObject(ms_LoginAppCompatButton, "textColor", new ArgbEvaluator(), Color.BLUE, Color.BLACK).setDuration(3000).start();
        progressDialog.show();
        doLogin();
    }

    private void doLogin() {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObject = object.getJSONObject("0");
                    if (ms_UserNameAppCompatEditText.getText().toString().trim().contentEquals(jsonObject.getString("username")) &&
                            ms_PasswordAppCompatEditText.getText().toString().trim().contentEquals(jsonObject.getString("password"))) {

                        SharedPreferences.Editor editor = getContext().getSharedPreferences("LOGIN_MS",Context.MODE_PRIVATE).edit();
                        editor.putBoolean("login", true);
                        editor.apply();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        AdminFragment llf = new AdminFragment();
                        ft.replace(R.id.frame, llf);
                        ft.commit();
                    }else new MaterialDialog.Builder(getContext()).content("User Name or Password Wrong !!!!").cancelable(true).show();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error.toString().contains("NoConnectionError")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("No Active Internet Connection :(")
                            .show();
                }else new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Something wrong. Please try after sometime :(")
                        .show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_USER);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
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
