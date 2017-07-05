/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rtsoftbd.siddiqui.drDipuMoni.helper.FontManager;
import com.rtsoftbd.siddiqui.drDipuMoni.model.AboutSocial;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.fromTextView)
    EditText ms_FromTextView;
    @BindView(R.id.subjectTextView)
    EditText ms_SubjectTextView;
    @BindView(R.id.bodyTextView)
    EditText ms_BodyTextView;
    @BindView(R.id.sendButton)
    AppCompatButton ms_SendButton;
    @BindView(R.id.fbdButton)
    AppCompatButton ms_FbdButton;
    @BindView(R.id.gPlusButton)
    AppCompatButton ms_GPlusButton;
    @BindView(R.id.twitterButton)
    AppCompatButton ms_TwitterButton;
    @BindView(R.id.emailTextView)
    TextView ms_EmailTextView;

    private String from, subject, body;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        ButterKnife.bind(this, view);
        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(view.findViewById(R.id.icons_container), iconFont);
        ms_EmailTextView.setText(getResources().getString(R.string.fa_icon_envelope)
                .concat(" " + getResources().getString(R.string.sendEmail)));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.sendButton, R.id.fbdButton, R.id.gPlusButton, R.id.twitterButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendButton:
                sendEmail();
                break;
            case R.id.fbdButton:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AboutSocial.getSlink1Link()));
                startActivity(browserIntent);
                break;
            case R.id.gPlusButton:
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(AboutSocial.getSlink3Link()));
                startActivity(browserIntent2);
                break;
            case R.id.twitterButton:
                Intent browserIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(AboutSocial.getSlink2Link()));
                startActivity(browserIntent3);
                break;
        }
    }

    private void sendEmail() {
        if (!validate()) return;

        Log.i("Send email", ".....");
        String[] TO = {AboutSocial.getAdminEmail()};
        String[] CC = {"ssazal.14@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("qqq", "Finished sending email...");
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean validate() {
        boolean valid = true;

        from = ms_FromTextView.getText().toString().trim();
        subject = ms_SubjectTextView.getText().toString().trim();
        body = ms_BodyTextView.getText().toString().trim();

        if (from.isEmpty() || body.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            ms_FromTextView.setError(getResources().getString(R.string.emailError));
            valid = false;
        } else ms_FromTextView.setError(null);

        if (subject.isEmpty()) {
            ms_SubjectTextView.setError(getResources().getString(R.string.subjectError));
            valid = false;
        } else ms_SubjectTextView.setError(null);

        if (body.isEmpty()) {
            ms_BodyTextView.setError(getResources().getString(R.string.bodyError));
            valid = false;
        } else ms_BodyTextView.setError(null);

        return valid;
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
