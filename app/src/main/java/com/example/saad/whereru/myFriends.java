package com.example.saad.whereru;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myFriends.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myFriends#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myFriends extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String[] mobileArray ;
    int[] ids;
    Integer[] imageid ;
    String times[] = {};


    // TODO: Rename and change types of parameters

    //final Context c=getActivity();
    public boolean attached = false;
    private profile.OnFragmentInteractionListener mListener;

    public myFriends() {
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
                (R.layout.fragment_my_friends, container, false);
        //if this is not the first run, meaning the database already contains some News and we are just getting them and showing nthem here.

        if (attached) {

        }


        TextView headerText=(TextView)fragmentView_latest_news.findViewById(R.id.headerTextFrnds);
        ImageView headerImage=(ImageView) fragmentView_latest_news.findViewById(R.id.headerImageFrnds) ;
        ListView listView = (ListView)fragmentView_latest_news.findViewById(R.id.listViewFrnds);


        headerImage.setImageResource(R.drawable.friends);
        headerText.setText("My friends");


        updateList();

        final CustomList customList = new CustomList(getActivity(), mobileArray,ids, imageid, times,2);


        //txt.setVisibility(View.VISIBLE);

        listView.setEnabled(true);


        listView.setAdapter(customList);
        listView.setVisibility(View.VISIBLE);




        return fragmentView_latest_news;


    }


    private void showError(String s) {

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
    public void updateList() {
        mobileArray = new String[MapsActivity.p.frnds.size()];
        imageid = new Integer[mobileArray.length];
        times= new String[mobileArray.length];
        ids=new int[mobileArray.length];
        for (int c = 0; c < mobileArray.length; c++) {
            mobileArray[c]=MapsActivity.p.frnds.get(c).name;

            //names[c] = MainActivity.userFriendList[c].name;

            times[c]="Just Now";

            ids[c]=MapsActivity.p.frnds.get(c).id;
            imageid[c]= MapsActivity.p.frnds.get(c).imageID;
        }


    }
    private boolean formatChecker(String value) {

        if (value.length() == 0) {
            return true;
        }
        try {
            int i = Integer.parseInt(value);
        } catch (Exception r) {
            return false;
        }

        return true;
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


}