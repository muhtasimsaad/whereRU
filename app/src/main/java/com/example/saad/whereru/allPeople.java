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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link allPeople.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link allPeople#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allPeople extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static ProgressDialog pdMarkers;
    public static String name ="";
    String[] mobileArray ;
    int[] ids;
    Integer[] imageid ;
    String times[] = {};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean calenderFlag = true;
    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;
    //final Context c=getActivity();
    public boolean attached = false;
    private profile.OnFragmentInteractionListener mListener;

    public allPeople() {
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
                (R.layout.fragment_all_people, container, false);
        //if this is not the first run, meaning the database already contains some News and we are just getting them and showing nthem here.

        if (attached) {

        }



        TextView headerText=(TextView)fragmentView_latest_news.findViewById(R.id.headerTextSearch);
        headerText.setText("Find friends");


        //headerText.setText("Find a Friend");
        pdMarkers = new ProgressDialog(getActivity());
        pdMarkers.setTitle("Downloading");
        pdMarkers.setMessage("Locations");
        pdMarkers.setCancelable(false);
        pdMarkers.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {


                String times[] = {"","","","","","","","",};
                //When download ends
                try {

                    int count = name.length() - name.replace("-", "").length();
                    mobileArray=new String[count];
                    ids=new int[count];
                    imageid = new Integer[count];
                    times=new String[count];
                    for (int aa = 0; aa < count; aa++) {
                        //parsing the downloaded data
                        int c = name.indexOf(",");
                        String n = name.substring(0, c);
                        mobileArray[aa] = n;
                        times[aa]="";
                        int cc = name.indexOf("-");
                        int avatar = Integer.parseInt(name.substring(c + 1, cc));
                        imageid[aa] = avatar;
                        name = name.substring(cc + 1);
                    }



               ListView listView = (ListView) fragmentView_latest_news.findViewById(R.id.listViewSearch);


                    final CustomList customList = new CustomList(getActivity(), mobileArray,ids, imageid, times,1);
                    listView.setEnabled(true);
                    listView.setAdapter(customList);

                } catch (Exception r) {
                    Toast.makeText(getActivity(), r.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });



        asd();



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
    private void asd(){

        try{pdMarkers.show();}
        catch (Exception rr){Toast.makeText(getActivity(),"Progress dialog error",Toast.LENGTH_LONG).show();}
        logger l = new logger(getActivity(), getActivity(), "--");
        l.execute("dlAll", "", "");

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