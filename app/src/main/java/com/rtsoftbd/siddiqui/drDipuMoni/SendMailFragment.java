/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.LoadDropDown;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Union_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Upozila_MS;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Word_MS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Use the {@link SendMailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.upozilaSpinner)
    AppCompatSpinner ms_UpozilaSpinner;
    @BindView(R.id.unionSpinner)
    AppCompatSpinner ms_UnionSpinner;
    @BindView(R.id.wordSpinner)
    AppCompatSpinner ms_WordSpinner;
    @BindView(R.id.subject)
    AppCompatEditText ms_Subject;
    @BindView(R.id.sms)
    AppCompatEditText ms_Sms;
    @BindView(R.id.send)
    AppCompatButton ms_Send;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String upozilaName="sazal", unionName="sazal", wordName="sazal";

    List<Upozila_MS> upozila_msList = new ArrayList<>();
    List<Union_MS> union_msList = new ArrayList<>();
    List<Word_MS> word_msList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public SendMailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendMailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendMailFragment newInstance(String param1, String param2) {
        SendMailFragment fragment = new SendMailFragment();
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
        View view = inflater.inflate(R.layout.fragment_send_mail, container, false);
        unbinder = ButterKnife.bind(this, view);

        new LoadDropDown(getContext());


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


        ms_UpozilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Upozila_MS upozila_ms1 = (Upozila_MS) parent.getItemAtPosition(position);
                union_msArrayAdapter.clear();
                upozilaName = upozila_ms1.getUpozila_name();
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
                unionName = union_ms1.getUnion_name();
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
                wordName = word_ms1.getWord_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    @OnClick(R.id.send)
    public void onViewClicked() {
        String message = ms_Sms.getText().toString();
        String subject = ms_Subject.getText().toString();

        //Toast.makeText(getContext(), upozilaName+"->"+unionName+"->"+wordName, Toast.LENGTH_LONG).show();
        if (message.isEmpty()) ms_Sms.setError("please write something");
        else if (message.length()>1000) ms_Sms.setError("max sms length is 1000 alphabets");
        else {
            if (upozilaName.contentEquals("Send All") && unionName.contentEquals("Send All") && wordName.contentEquals("Send All")){
                sendSMS(false, false,false, message,subject);
            }else if (unionName.contentEquals("Send All") && wordName.contentEquals("Send All")){
                sendSMS(true, false, false, message,subject);
            }else if (wordName.contentEquals("Send All")){
                sendSMS(true,true,false, message,subject);
            }else{
                sendSMS(true, true, true, message,subject);
            }
        }
    }

    private void sendSMS(final boolean upozila, final boolean union, final boolean word, final String sms, final String subject) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Sending Email......");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.SEND_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    new MaterialDialog.Builder(getContext())
                            .title(object.getString("confirm_status"))
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();

                params.put(ApiUrl.EMAIL_DES, sms);
                params.put(ApiUrl.EMAIL_SUBJECT, subject);
                if (upozila)
                    params.put(ApiUrl.KEY_SMS_UPOZILA, upozilaName);
                if (union)
                    params.put(ApiUrl.KEY_SMS_UNION, unionName);
                if (word)
                    params.put(ApiUrl.KEY_SMS_WORD, wordName);


                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
