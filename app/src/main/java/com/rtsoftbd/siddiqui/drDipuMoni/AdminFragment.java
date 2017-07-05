/*
 * Copyright By Noor Nabiul Alam Siddiqui  (c) 2017.
 *  www.fb.com/sazal.ns
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rtsoftbd.siddiqui.drDipuMoni.customeAdapter.CustomListAdapterUser;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.ApiUrl;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.AppController;
import com.rtsoftbd.siddiqui.drDipuMoni.helper.FontManager;
import com.rtsoftbd.siddiqui.drDipuMoni.model.Status;
import com.rtsoftbd.siddiqui.drDipuMoni.model.User;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.userListView)
    SwipeMenuListView ms_UserListView;
    private List<User> users = new ArrayList<>();
    private CustomListAdapterUser listAdapter;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private ProgressDialog progressDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
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

        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Admin Panel");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        ButterKnife.bind(this, view);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("working . . . . ");
        progressDialog.setCancelable(false);

        listAdapter = new CustomListAdapterUser(getActivity(), users);
        ms_UserListView.setAdapter(listAdapter);

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(view.findViewById(R.id.userListView), iconFont);

        loadData();

        ms_UserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final User user = (User) parent.getItemAtPosition(position);

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflater.inflate(R.layout.details, null);
                if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

                NetworkImageView imageView = (NetworkImageView) v.findViewById(R.id.image);
                AppCompatTextView name = (AppCompatTextView) v.findViewById(R.id.name);
                AppCompatTextView mobile = (AppCompatTextView) v.findViewById(R.id.mobile);
                AppCompatTextView email = (AppCompatTextView) v.findViewById(R.id.email);
                AppCompatTextView deg = (AppCompatTextView) v.findViewById(R.id.designation);
                AppCompatTextView gender = (AppCompatTextView) v.findViewById(R.id.gender);
                AppCompatTextView nid = (AppCompatTextView) v.findViewById(R.id.nid);
                AppCompatTextView upzila = (AppCompatTextView) v.findViewById(R.id.upozila);
                AppCompatTextView union = (AppCompatTextView) v.findViewById(R.id.union);
                AppCompatTextView wor = (AppCompatTextView) v.findViewById(R.id.word);
                AppCompatImageButton edit = (AppCompatImageButton) v.findViewById(R.id.editButton);
                final AppCompatImageButton delete = (AppCompatImageButton) v.findViewById(R.id.deleteButton);

                edit.setVisibility(View.GONE);

                name.setText(user.getName());
                mobile.setText(user.getPhone());
                email.setText(user.getEmail());
                deg.setText(user.getDeg());
                gender.setText(user.getGender());
                nid.setText(user.getNid());
                upzila.setText(user.getUpozila());
                union.setText(user.getUpo_union());
                wor.setText(user.getWord_cha());

                imageView.setImageUrl(ApiUrl.ASSETS_UPLOAD+user.getImage(), imageLoader);

               final MaterialDialog d =   new MaterialDialog.Builder(getActivity())
                        .customView(v, true)
                        .autoDismiss(true)
                        .show();

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new MaterialDialog.Builder(getActivity())
                                .content(getResources().getString(R.string.sure))
                                .positiveText("YES")
                                .positiveColor(getResources().getColor(R.color.material_red_700))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        progressDialog.show();
                                        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.DELETE_URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                              progressDialog.dismiss();
                                                if (response.contains("false")) {
                                                    FragmentManager fm = getFragmentManager();
                                                    FragmentTransaction ft = fm.beginTransaction();
                                                    AdminFragment llf = new AdminFragment();
                                                    ft.replace(R.id.frame, llf);
                                                    ft.commit();
                                                    d.dismiss();
                                                }else
                                                    new AlertDialog.Builder(getContext())
                                                            .setTitle(getResources().getString(R.string.replay))
                                                            .setMessage("Something Wrong.")
                                                            .show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                progressDialog.dismiss();
                                                error.printStackTrace();
                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                params.put(ApiUrl.KEY_DELETE_REQUEST, user.getVoter_id());

                                                return params;
                                            }
                                        };

                                        AppController.getInstance().addToRequestQueue(request);
                                    }
                                })
                                .negativeText("NO")
                                .show();
                    }
                });
            }
        });
        return view;
    }

    private void loadData() {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.i("resposnse", response);
                    Log.i("test",jsonObject.toString());
                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String dynamicKey = (String) keys.next();

                        if (!dynamicKey.contains("error")) {
                            JSONObject object = jsonObject.getJSONObject(dynamicKey);

                            User user = new User();
                            user.setVoter_id(object.getString("voter_id"));
                            user.setName(object.getString("name"));
                            user.setPhone(object.getString("phone"));
                            user.setEmail(object.getString("email"));
                            user.setGender(object.getString("gender"));
                            user.setUpozila(object.getString("upozila"));
                            user.setUpo_union(object.getString("upo_union"));
                            user.setWord_cha(object.getString("word"));
                            user.setImage(object.getString("picture"));
                            user.setDeg(object.getString("designation"));
                            user.setNid(object.getString("national_Id"));

                            users.add(user);

                        }
                    }
                    listAdapter.notifyDataSetChanged();

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
                params.put(ApiUrl.KEY_DATA_REQUEST, ApiUrl.TABLE_VOTER_INFO);

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem call = new SwipeMenuItem(getContext());
                call.setBackground(new ColorDrawable(Color.rgb(224, 224, 224)));
                call.setWidth(dp2px(70));
                call.setIcon(getResources().getDrawable(R.drawable.ic_call));
                menu.addMenuItem(call);

                SwipeMenuItem sms = new SwipeMenuItem(getContext());
                sms.setWidth(dp2px(70));
                sms.setBackground(new ColorDrawable(Color.rgb(192, 192, 192)));
                sms.setIcon(getResources().getDrawable(R.drawable.ic_textsms));
                menu.addMenuItem(sms);

            }
        };

        ms_UserListView.setMenuCreator(creator);

        ms_UserListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                User user = users.get(position);
                switch (index) {
                    case 0:
                        call(user);
                        break;
                    case 1:
                        sms(user);
                        break;
                }

                return false;
            }
        });

        ms_UserListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

    }

    private void call(User user) {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+user.getPhone()));
        startActivity(callIntent);

    }

    private void sms(final User user) {
        new MaterialDialog.Builder(getContext())
                .title("Send Message")
                .input("Message", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Uri uri = Uri.parse("smsto:" + user.getPhone());
                        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        smsSIntent.putExtra("sms_body", input.toString());
                        try{
                            startActivity(smsSIntent);
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Your sms has failed...",
                                    Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }
                    }
                })
                .cancelable(true)
                .show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        menu.removeItem(R.id.action_about);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sms:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SendSmsFragment llf = new SendSmsFragment();
                ft.replace(R.id.frame, llf);
                ft.commit();
                return true;
            case R.id.action_about:
                new MaterialDialog.Builder(getContext())
                        .customView(R.layout.about_us, true)
                        .show();
                return true;
            case R.id.action_status:
                new MaterialDialog.Builder(getContext())
                        .title("Post Status")
                        .inputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)
                        .inputRange(1,1000)
                        .input(null, null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
                                final String today = dateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime());
                                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Posting Status .....");
                                progressDialog.show();

                                StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.STATUS_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        if (response.contains("false")){
                                            new MaterialDialog.Builder(getContext())
                                                    .content("Done")
                                                    .show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        error.printStackTrace();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put(ApiUrl.KEY_STATUS_DATA, input.toString());
                                        params.put(ApiUrl.KEY_USER_STATUS_ID, "100");
                                        params.put(ApiUrl.KEY_STATUS_DATE_, today);

                                        return params;
                                    }
                                };

                                AppController.getInstance().addToRequestQueue(request);

                            }
                        })
                        .cancelable(true)
                        .show();
                return true;
            case R.id.action_logout:
                SharedPreferences.Editor editor = getContext().getSharedPreferences("LOGIN_MS",Context.MODE_PRIVATE).edit();
                editor.putBoolean("login", false);
                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                return true;
            case R.id.action_email:
                 fm = getFragmentManager();
                 ft = fm.beginTransaction();
                ft.replace(R.id.frame, new SendMailFragment());
                ft.commit();
                return true;
            case R.id.action_edit_status:
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, new StatusFragment());
                ft.commit();
                return true;
            case R.id.action_add_member:
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, new RegFragment());
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
