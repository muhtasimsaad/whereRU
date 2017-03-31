package com.example.saad.whereru;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link notifications.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notifications extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static ProgressDialog pdMarkersRqst;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean calenderFlag = true;

    static String name ="";
    String[] mobileArray ;
    int[] ids;
    Integer[] imageid ;
    String times[] = {};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //final Context c=getActivity();
    public boolean attached = false;
    private profile.OnFragmentInteractionListener mListener;

    public notifications() {
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
                (R.layout.fragment_notifications, container, false);

        if(attached){

        }

        pdMarkersRqst = new ProgressDialog(getActivity());
        pdMarkersRqst.setTitle("Downloading");
        pdMarkersRqst.setMessage("Please wait");
        pdMarkersRqst.setCancelable(false);

        pdMarkersRqst.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                String arr = logger.rqsts;
                //riya,1$1-akib,2$0-sakib,3$2-
                // if(debug)Toast.makeText(getApplicationContext(),"Frndlist String: "+logger.frndlst,Toast.LENGTH_LONG).show();


                try {
                    int count = arr.length() - arr.replace("-", "").length();

                    mobileArray = new String[count];
                    ids = new int[count];
                    imageid = new Integer[count];
                    times = new String[count];
                    for (int aa = 0; aa < count; aa++) {


                        int c = arr.indexOf(",");
                        String n = arr.substring(0, c);

                        int cc = arr.indexOf("$");
                        int gid = Integer.parseInt(arr.substring(c + 1, cc));

                        int cc2 = arr.indexOf("-");
                        int avatar = Integer.parseInt(arr.substring(cc + 1, cc2));
                        mobileArray[aa] = n;
                        ids[aa] = gid;
                        imageid[aa] = R.drawable.flash;
                        times[aa] = "";


                        arr = arr.substring(cc2 + 1);

                    }


                    final CustomList customList2 = new CustomList(getActivity(), mobileArray, ids, imageid, times, 3);



                    ListView listView = (ListView) fragmentView_latest_news.findViewById(R.id.listViewNotification);
                    listView.setAdapter(customList2);


                }
                catch (Exception er){

                    Toast.makeText(getActivity(),er.getMessage(),Toast.LENGTH_LONG).show();

                }


            }
        });






       TextView headerText=(TextView)fragmentView_latest_news.findViewById(R.id.headerTextNotification);
        headerText.setText("Friend Requests");

        logger l = new logger(getActivity(), getActivity(), "--");
        l.execute("searchRqst", "", "");
        pdMarkersRqst.show();


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

    private boolean formatChecker(String value){

        if(value.length()==0){return  true;}
        try{int i=Integer.parseInt(value);}
        catch (Exception r){return false;}

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
