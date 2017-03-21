package com.example.saad.whereru;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.saad.whereru.R.drawable.add;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private int[] ids;
    private Integer[] imageid;
    private String [] times;
    private Activity context;
    private int swtch;

    public CustomList(Activity context, String[] names,int[] ids,Integer[] imageid,String[] times,int s) {
        super(context, R.layout.list_layout, names);

        this.context = context;
        this.swtch=s;
        this.ids=ids;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;
        this.times=times;
    }

            /*The class responsible for all the lists shown in the app.
            Such as the list in magnigying glass in main window or the
            lists in the accounts section.

            the int swtch determines for whom the view is to be generated
            and generates the view accordingly.

            */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        final Button    b=  (Button)   listViewItem.findViewById(R.id.button5);


        final Button    no=  (Button)   listViewItem.findViewById(R.id.noButton);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(swtch!=3){no.setVisibility(View.GONE);}

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.textViewName2);
        // TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        image.setMaxWidth(100);image.setMaxHeight(100);
        b.setText("");
        if(swtch!=3){no.setVisibility(View.GONE);textViewTime .setVisibility(View.VISIBLE);}
        else{
            no.setVisibility(View.VISIBLE);
            no.setBackgroundResource(R.drawable.no);
            b.setBackgroundResource(R.drawable.yes);
            textViewTime .setVisibility(View.GONE);}
        if(swtch==1){b.setBackgroundResource(R.drawable.add);}
        if(swtch==2){b.setBackgroundResource(R.drawable.delete);}
        textViewName.setText(names[position]);
        textViewTime.setText(times[position]);
        //  textViewDesc.setText(desc[position]);
        image.setImageResource(imageid[position]);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(swtch==3){

                    logger l=new logger (context,context,"asd");
                    l.execute("rejectRqst",Login.userID+"",ids[position]+"");

                }

            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ID: "+ids[position], Toast.LENGTH_SHORT).show();
               if(swtch==0){ try{
                    MapsActivity.goToFlag=position;
                    MapsActivity.pdMarkers3.show();
                    MapsActivity.pdMarkers3.dismiss();}
                catch (Exception r){Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();}}
                if(swtch==1){ try{

                    logger l = new logger( context, context, "--");
                    l.execute("sendRqst", Login.userID+"", position+"");

                }
                catch (Exception r){Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();}}
                if(swtch==2){ try{

                    logger l = new logger( context, context, "--");
                    l.execute("delFrnd", Login.userID+"", ids[position]+"");

                   MapsActivity.deleteFrnd(ids[position]);
                }
                catch (Exception r){Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();}}
                if(swtch==3){

                    if(swtch==3){

                        logger l=new logger (context,context,"asd");
                        l.execute("acceptRqst",Login.userID+"",ids[position]+"");

                    }
                }
            }
        });

        return  listViewItem;
    }
}
