package com.example.saad.whereru;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Logger;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.Account;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    String[] mobileArray ;
    int[] ids;
    Integer[] imageid ;
    String[] times;
    public static ProgressDialog pdMarkers;
    public static ProgressDialog pdMarkers2;
    public static ProgressDialog pdMarkers3;
    LatLng ltlng;
    static int goToFlag=-1;
    SQLiteDatabase db;
    static packet p=new packet();
    private GoogleMap mMap;
    private boolean markerFlag=false;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    final Context context = this;
    boolean debug=false;
    Date cooldown=new Date();
    boolean firstTime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        if(Login.userID==-1){
            Intent i=new Intent(MapsActivity.this,Login.class);
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final ListView ls = (ListView) findViewById(R.id.listView);
        ls.setVisibility(View.GONE);
         TextView t=(TextView)findViewById(R.id.textView);
         t.setText("");

        InitiateDatabase();
        final Button acnts=(Button)findViewById(R.id.accounts);
        acnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MapsActivity.this, navigation_accounts.class);
                startActivity(i);
            }
        });
        readyTheProgressDilog();
        final Button MagnifyingGlass = (Button) findViewById(R.id.search_buttom);
        final Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                updateFriendList();
                downloadMarkers();
                }
                catch (Exception rr){Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        MagnifyingGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      updateList();
                    final CustomList customList = new CustomList((Activity) context, mobileArray,ids, imageid,times,0);
                     ls.setAdapter(customList);
                    ls.setVisibility(View.VISIBLE);
                    MagnifyingGlass.setVisibility(View.GONE);
      }
        });


        logger l=new logger (this,this,"asd");
        l.execute("dlFriendList","","");

        pdMarkers2 = new ProgressDialog(MapsActivity.this);
        pdMarkers2.setTitle("Downloading");
        pdMarkers2.setMessage("Receiving Co-Ordinates");
        pdMarkers2.setCancelable(false);
        pdMarkers2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {



                upDateMarkers(logger.markers);
                putMarkers();
                cache();

            }
        });


 if(!cached()) {
     if(debug){Toast.makeText(getApplicationContext(), "Downloading For ID: "+Login.userID, Toast.LENGTH_SHORT).show();}
    pdMarkers = new ProgressDialog(MapsActivity.this);
    pdMarkers.setTitle("Downloading");
    pdMarkers.setMessage("Please Wait");
    pdMarkers.setCancelable(false);

    pdMarkers.show();

    pdMarkers.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if(debug){Toast.makeText(getApplicationContext(), "Frnd List: "+logger.frndlst, Toast.LENGTH_SHORT).show();}

            updateFriendList();
            downloadMarkers();
        }
    });
 }
else {
    if(debug){
        Toast.makeText(getApplicationContext(), "Cache Found :" + p.frnds.size(), Toast.LENGTH_SHORT).show();}
     markerFlag = true;
 }

        pdMarkers3 = new ProgressDialog(MapsActivity.this);
        pdMarkers3.setTitle("Downloading");
        pdMarkers3.setMessage("Please Wait");
        pdMarkers3.setCancelable(false);



        pdMarkers3.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(p.frnds.get(goToFlag).position));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                ls.setVisibility(View.GONE);
                MagnifyingGlass.setVisibility(View.VISIBLE);
            }
        });


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             if(debug){   Toast.makeText(context, "ashse", Toast.LENGTH_LONG).show();}
                try{ mMap.moveCamera(CameraUpdateFactory.newLatLng(ltlng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16));}
                catch (Exception r){Toast.makeText(context, r.getMessage(), Toast.LENGTH_LONG).show();}
                ls.setVisibility(View.GONE);
                MagnifyingGlass.setVisibility(View.VISIBLE);


            }
        });


    }

    private void downloadMarkers(){


        String s="";

for(int i=0;i<p.frnds.size();i++){

    s=s+p.frnds.get(i).id+"-";

}

        if(debug)Toast.makeText(getApplicationContext(), "Downloading markers :"+s, Toast.LENGTH_LONG).show();
        logger l=new logger (this,this,"asd");
        l.execute("dlMarkers",s,"");
        if(debug){ Toast.makeText(getApplicationContext(), "Downloading markers :"+s, Toast.LENGTH_LONG).show();}


        pdMarkers2 = new ProgressDialog(MapsActivity.this);
        pdMarkers2.setTitle("Downloading");
        pdMarkers2.setMessage("Receiving Co-Ordinates");
        pdMarkers2.setCancelable(false);
        pdMarkers2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {



                //TextView t=(TextView)findViewById(R.id.textView);
                //t.setText(logger.markers);
                //upDateMarkers(logger.markers);
                //putMarkers();
                 if(debug)Toast.makeText(getApplicationContext(),"Marker String :"+logger.markers,Toast.LENGTH_LONG).show();
                 upDateMarkers(logger.markers);
                 putMarkers();

                cache();
            }
        });
         try{pdMarkers2.show();}
         catch (Exception r){Toast.makeText(getApplicationContext(), r.getMessage(), Toast.LENGTH_LONG).show();}



    }
    public void updateFriendList() {


        String arr = logger.frndlst;
        //riya,1$1-akib,2$0-sakib,3$2-
       if(debug)Toast.makeText(getApplicationContext(),"Frndlist String: "+logger.frndlst,Toast.LENGTH_LONG).show();
        int count = arr.length() - arr.replace("-", "").length();
        for (int aa = 0; aa < count; aa++) {


            int c  = arr.indexOf(",");
            String n = arr.substring(0, c);

            int cc = arr.indexOf("$");
            int gid = Integer.parseInt(arr.substring(c + 1, cc));

            int cc2 = arr.indexOf("-");
            int avatar = Integer.parseInt(arr.substring(cc + 1, cc2));
            p.frnds.add( new friend(n,gid,avatar));
            if(debug)Toast.makeText(getApplicationContext(),"Frnd added: "+n+"--"+gid+"--"+avatar,Toast.LENGTH_LONG).show();
            arr = arr.substring(cc2 + 1);

        }




        if (debug) {
            Toast.makeText(getApplicationContext(), "Updated Friend List", Toast.LENGTH_LONG).show();
        }
    }
    public void upDateMarkers(String markerString) {
        if (debug) {
            Toast.makeText(getApplicationContext(), "Updating Markers", Toast.LENGTH_LONG).show();
        }
        int count = markerString.length() - markerString.replace("^", "").length();
        for (int aa = 0; aa < count; aa++) {
            int c  = markerString.indexOf(",");
            double lat = Double.parseDouble(markerString.substring(0, c));
            int d  = markerString.indexOf("$");
            double lon = Double.parseDouble(markerString.substring(c+1, d));

            int e = markerString.indexOf("^");
            int min = Integer.parseInt(markerString.substring(d + 1, e));
              p.frnds.get(aa).position=new LatLng(lat,lon);
             p.frnds.get(aa).time=min;

            markerString = markerString.substring(e + 1);
            if(debug){  Toast.makeText(this, "Updated Markers ", Toast.LENGTH_LONG).show();}

    }

        if (debug) {

            Toast.makeText(this, "Updated Markers ", Toast.LENGTH_LONG).show();
        }

    }

private void  putMarkers(){

    for(int i=0;i<p.frnds.size();i++) {


        Date dt=new Date();
        String s=timeConverter(dt,p.frnds.get(i).time);


        mMap.addMarker(new MarkerOptions()
                .position(p.frnds.get(i).position)
                .title(p.frnds.get(i).name)
                .snippet(s)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.batman)));
    }
}



    private boolean cached(){

        InitiateDatabase();
        boolean result=readDatabase();

        return result;
    }
    private void cache(){

        updateDatabase();
    }



    private void InitiateDatabase() {
        db = openOrCreateDatabase("whereRU", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS frnds(" +

                "userID INTEGER," +
                "id INTEGER," +
                "name VARCHAR," +
                "latitude DOUBLE," +
                "longitude DOUBLE," +
                "imageID INTEGER," +
                "time INTEGER);");


    }
    //String n,LatLng p,int i,int ii,Date d
    private boolean readDatabase() {

        boolean resultFlag=false;
        if(debug) Toast.makeText(getApplicationContext(), "Reading Database for ID: "+ Login.userID, Toast.LENGTH_LONG).show();
       Cursor c = db.rawQuery("SELECT * FROM frnds where userID='" + Login.userID + "'", null);
    int i = 0;
    if (c.getCount() != 0) {

        while (c.moveToNext()) {
            resultFlag=true;

               friend f = new friend(c.getString(2), new LatLng(c.getDouble(3), c.getDouble(4)), c.getInt(1), c.getInt(5), c.getInt(6));
           boolean adderFlag=true;
            //cgecking if the sql row is already in RAM
            for(int counter=0;counter<p.frnds.size();counter++){if(p.frnds.get(counter).id==c.getInt(1)){adderFlag=false;}}

            if(adderFlag){p.frnds.add(f);
            logger.frndlst=packet.frndlst+f.name+","+f.id+"$"+f.imageID+"-";}


        }
          if(debug) Toast.makeText(getApplicationContext(), "Database Read Successfuly; Frnds: " +p.frnds.size(), Toast.LENGTH_LONG).show();
  }


return resultFlag;
    }
    private void updateDatabase(){
        if(debug) Toast.makeText(getApplicationContext(), "Trying to update for ID: "+Login.userID, Toast.LENGTH_SHORT).show();
//        if(debug) Toast.makeText(getApplicationContext(),
//                p.frnds.get(0).name+"---"+p.frnds.get(0).position.latitude+"---"+p.frnds.get(0).position.longitude
//                , Toast.LENGTH_SHORT).show();

        try {
     for (int i = 0; i < p.frnds.size(); i++) {
        int chk = p.frnds.get(i).id;

        Cursor c = db.rawQuery("SELECT * FROM frnds where userID='" + Login.userID + "' and id='" + chk + "'", null);

        if (c.getCount() == 0) {

            db.execSQL("INSERT INTO frnds VALUES('" + Login.userID + "','" + chk + "','" + p.frnds.get(i).name + "'," +
                    "'" + p.frnds.get(i).position.latitude + "','" + p.frnds.get(i).position.longitude + "','" + p.frnds.get(i).imageID + "','" + p.frnds.get(i).time + "');");
        } else {

            db.execSQL("UPDATE frnds set latitude='" + p.frnds.get(i).position.latitude + "',longitude=" +
                    "'" + p.frnds.get(i).position.longitude + "',time='" + p.frnds.get(i).time + "' where" +
                    " userID='" + Login.userID + "' and id='" + chk + "';");
        }
    }
if(debug) Toast.makeText(getApplicationContext(), "Database Updated for ID: "+Login.userID, Toast.LENGTH_SHORT).show();

}catch (Exception r ){
   // TextView t=(TextView)findViewById(R.id.textView);
   // t.setText(r.getMessage());
}


    }





















    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        final Button MagnifyingGlass = (Button) findViewById(R.id.search_buttom);
        final ListView ls = (ListView) findViewById(R.id.listView);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                ls.setVisibility(View.GONE);
                MagnifyingGlass.setVisibility(View.VISIBLE);
            }
        });

if(markerFlag){markerFlag=false;putMarkers();

}

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onLocationChanged(Location location) {



        if(firstTime) {
             logger l = new logger(this, this, "asd");
            l.execute("updateLocation", location.getLatitude() + "", location.getLongitude() + "");
            firstTime=false;
            Toast.makeText(getApplicationContext(),"Updated First Time",Toast.LENGTH_LONG).show();
        }


        else{

            Date now=new Date();
            if(now.getSeconds()-cooldown.getSeconds()>60){
                logger l = new logger(this, this, "asd");
                l.execute("updateLocation", location.getLatitude() + "", location.getLongitude() + "");
                cooldown=new Date();
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
            }

        }

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();



        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        ltlng=latLng;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ltlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        //mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera






        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,   this);
        }

    }


    public void readyTheProgressDilog(){

        pdMarkers = new ProgressDialog(MapsActivity.this);
        pdMarkers.setTitle("Downloading");
        pdMarkers.setMessage("Locations");
        pdMarkers.setCancelable(false);


        pdMarkers.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                mMap.moveCamera(CameraUpdateFactory.newLatLng(ltlng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            }
        });



    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

   private void updateList(){
/*
          mobileArray = new String[]{"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
        imageid = new Integer[]{R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,
                R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,};
        times = new String[]{"00:00","00:00","00:00","00:00","00:00","00:00","00:00","00:00",};
*/
       Date dt=new Date();


       mobileArray=new String[p.frnds.size()];
       imageid=new Integer[p.frnds.size()];
       times=new String[p.frnds.size()];
       ids=new int[p.frnds.size()];
        for(int i=0;i<p.frnds.size();i++){
            ids[i]=p.frnds.get(i).id;
           mobileArray[i]=p.frnds.get(i).name;
           imageid[i]=p.frnds.get(i).imageID;
           times[i]=timeConverter(dt,p.frnds.get(i).time);
       }



    }
private String timeConverter(Date now,int user){



String s="";
    double temp= (now.getTime()-(user*60*1000));
    temp=temp/1000;
    temp=temp/60;
    if(debug){  Toast.makeText(getApplicationContext(),"now: "+now+" User: "+user,Toast.LENGTH_LONG).show();}
if(temp<60){s=temp+" Mins ago";}
  else{
temp=temp/60;
    if(temp<24){s=temp+" Hours ago";}
    else{
        temp=temp/24;
        if(temp<365){s=temp+" Days ago";}
        else{
            int year=(int)temp/365;
            int day=(int)temp%365;
            s=year +" Year(s) "+day+" Days ago";
        }
    }
}

    return s;
}

public static void deleteFrnd(int id){
for(int i=0;i<p.frnds.size();i++){
    if(p.frnds.get(i).id==id){p.frnds.remove(i);}

}


}


}
