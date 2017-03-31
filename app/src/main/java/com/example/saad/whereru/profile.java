package com.example.saad.whereru;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean calenderFlag = true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //final Context c=getActivity();
    public boolean attached = false;
    private OnFragmentInteractionListener mListener;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        Bundle args = new Bundle();

        profile fragment = new profile();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private View fragmentView_latest_news;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView_latest_news = inflater.inflate
                (R.layout.fragment_profile, container, false);
        //if this is not the first run, meaning the database already contains some News and we are just getting them and showing nthem here.

        if(attached){

        }
        final Switch username=(Switch)fragmentView_latest_news.findViewById(R.id.switchUsername);
        final Switch password=(Switch)fragmentView_latest_news.findViewById(R.id.switchPassword);
        final Switch image=   (Switch)fragmentView_latest_news.findViewById(R.id.switchImage);
        final Button saveProfile=(Button)fragmentView_latest_news.findViewById(R.id.saveProfile);
        final Button cancelProfile=(Button)fragmentView_latest_news.findViewById(R.id.cancelProfile);
        saveProfile.setEnabled(false);

            username.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    usernameController(username.isChecked());
                    if(username.isChecked() || password.isChecked() || image.isChecked()){saveProfile.setEnabled(true);}
                    else{saveProfile.setEnabled(false);}
                }
            });
        password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordController(password.isChecked());
                if(username.isChecked() || password.isChecked() || image.isChecked()){saveProfile.setEnabled(true);}
                else{saveProfile.setEnabled(false);}
            }
        });
        image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                avatarController(image.isChecked());
                if(username.isChecked() || password.isChecked() || image.isChecked()){saveProfile.setEnabled(true);}
                else{saveProfile.setEnabled(false);}
            }
        });

        cancelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // do something on back.
                    Intent i=new Intent(getActivity(),MapsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

            }
        });
        usernameController(false);
        passwordController(false);
        avatarController(false);


        return fragmentView_latest_news;



    }


    private void showError(String s){

        final Snackbar sb = Snackbar.make(getView(),
                s, Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE);

        sb.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.dismiss();
            }
        });
        sb.show();



    }

    private void shutDownLayout(LinearLayout myLayout){


        for ( int i = 1; i < myLayout.getChildCount();  i++ ){
            View view = myLayout.getChildAt(i);
            view.setEnabled(false); // Or whatever you want to do with the view.
        }


    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction_news(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //  api.cancle_api();
        attached = false;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction_news(Uri uri);
    }


    private void usernameController(boolean flag){

        EditText et1=(EditText)fragmentView_latest_news.findViewById(R.id.usernameCurrent);
        EditText et2=(EditText)fragmentView_latest_news.findViewById(R.id.usernameNew);
        TextView t1=(TextView)fragmentView_latest_news.findViewById(R.id.textUsernameCurrent);
        TextView t2=(TextView)fragmentView_latest_news.findViewById(R.id.textUsernameNew);
        et1.setEnabled(flag);et2.setEnabled(flag);
        if(!flag){
            et1.setText("");et2.setText("");
            t1.setTextColor(Color.parseColor("#D3D3D3"));t2.setTextColor(Color.parseColor("#D3D3D3"));
        }
        else{t1.setTextColor(Color.parseColor("#9559f9"));t2.setTextColor(Color.parseColor("#9559f9"));}

    }
    private void passwordController(boolean flag){

        EditText et1=(EditText)fragmentView_latest_news.findViewById(R.id.passwordCurrent);
        EditText et2=(EditText)fragmentView_latest_news.findViewById(R.id.passwordNew);
        TextView t1=(TextView)fragmentView_latest_news.findViewById(R.id.textPasswordCurrent);
        TextView t2=(TextView)fragmentView_latest_news.findViewById(R.id.textPasswordNew);
        et1.setEnabled(flag);et2.setEnabled(flag);
        if(!flag){et1.setText("");et2.setText("");
             t1.setTextColor(Color.parseColor("#D3D3D3"));t2.setTextColor(Color.parseColor("#D3D3D3"));
        }
        else {t1.setTextColor(Color.parseColor("#9559f9"));;t2.setTextColor(Color.parseColor("#9559f9"));}
    }
    private void avatarController(boolean flag){
        ImageView t1=(ImageView)fragmentView_latest_news.findViewById(R.id.avatar);
        Button b1=(Button)fragmentView_latest_news.findViewById(R.id.galleryButton);
        Button b2=(Button)fragmentView_latest_news.findViewById(R.id.cameraButton);
        t1.setEnabled(flag);b1.setEnabled(flag);b2.setEnabled(flag);

    }

}