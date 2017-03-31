package com.example.saad.whereru;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class logger extends AsyncTask<String,Void,String> {
    public static String frndlst="";
    public static String markers="";
    public static String rqsts="";
    private ProgressDialog pd;

    String temp="";
    String flag="";
    private Activity activity;
    private ProgressDialog dialog;
    private Context context;
    AlertDialog alertDialog;
    Context contex;
    logger(Context cxt,Activity activity,String asd){
        contex=cxt;

    }




        public static void goToLink(){

        }
    @Override
    protected String doInBackground(String... params) {
        //String serverIP="192.168.0.103";
        String serverIP="tbone.000webhostapp.com";
        String login_url = "http://"+serverIP+"/login.php";

        try {


            //conditions here

            flag=params[0];
            String user_name = params[1];
            String password = params[2];

            if(flag.equals("dlFriendList")){

                login_url = "http://"+serverIP+"/downloadFriendList.php";}
            if(flag.equals("uploadImage")){

                login_url = "http://"+serverIP+"/uploadImage.php";}
            if(flag.equals("register")){

                login_url = "http://"+serverIP+"/Cregister.php";}
            if(flag.equals("acceptRqst")){

                login_url = "http://"+serverIP+"/acceptRqst.php";}
            if(flag.equals("rejectRqst")){

                login_url = "http://"+serverIP+"/rejectRqst.php";}
            if(flag.equals("changeAvatar")){

                login_url = "http://"+serverIP+"/changeAvatar.php";}
            if(flag.equals("searchRqst")){

                login_url = "http://"+serverIP+"/searchRqst.php";}

            if(flag.equals("dlMarkers")){

                login_url = "http://"+serverIP+"/downloadLocations.php";}
            if(flag.equals("dlImage")){

                login_url = "http://"+serverIP+"/downloadImage.php";}
            if(flag.equals("updateLocation")){

                login_url = "http://"+serverIP+"/updateLocation.php";}
            if(flag.equals("dlAll")){

                login_url = "http://"+serverIP+"/downloadAllPeople.php";}
            if(flag.equals("delFrnd")){

                login_url = "http://"+serverIP+"/delFrnd.php";}
            if(flag.equals("sendRqst")){

                login_url = "http://"+serverIP+"/sendRqst.php";}

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));





            //conditions here

            if(flag.equals("login"))
            {
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
            }

            if(flag.equals("dlFriendList")){



                String post_data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(Login.userID+"", "UTF-8") ;
                bufferedWriter.write(post_data);


            }
            if(flag.equals("dlMarkers")){



                String post_data = URLEncoder.encode("ids", "UTF-8") + "=" + URLEncoder.encode(user_name+"", "UTF-8") ;
                bufferedWriter.write(post_data);


            }
            if(flag.equals("dlImage")){



                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Login.userID+"", "UTF-8") ;
                bufferedWriter.write(post_data);



            }
            if(flag.equals("uploadImage")){



                String post_data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Login.userID+"", "UTF-8");
                bufferedWriter.write(post_data);

            }
            if(flag.equals("searchRqst")){



                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Login.userID+"", "UTF-8") ;
                bufferedWriter.write(post_data);


            }

            if(flag.equals("sendRqst")){



                String post_data = URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("reciever", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);


            }

            if(flag.equals("delFrnd") || flag.equals("register") || flag.equals("changeAvatar")){



                String post_data = URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("reciever", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);


            }
            if( flag.equals("acceptRqst") || flag.equals("rejectRqst")){



                String post_data = URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("reciever", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(post_data);


            }


            if(flag.equals("updateLocation")){

                Date dt=new Date();
                double min=dt.getTime()/60000;

                String post_data =URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(Login.userID+","+min, "UTF-8") + "&"
                        +
                        URLEncoder.encode("lattitude", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);


                //    if(params[2].length()>3 && params[1].length()>3){Intent myIntent = new Intent(contex, dummy.class);
                //    contex.startActivity(myIntent);}


            }


            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;temp+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();




    }

    @Override
    protected void onPostExecute(String result) {


        //conditions here


        // prasing the value came in
        if (flag.equals("login")) {
        Login.pdMarkersLogin.dismiss();
            try {
                int c = result.indexOf(",");
                Login.userID = Integer.parseInt(result.substring(0, c));
                AlertDialog.Builder builder = new AlertDialog.Builder(contex);
                builder.setTitle("login Status");
                builder.setMessage("Welcome");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent myIntent = new Intent(contex, MapsActivity.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        contex.startActivity(myIntent);


                    }
                });
                builder.show();
            } catch (Exception rr) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contex);
                builder.setTitle("login Status");
                builder.setMessage(temp);
                builder.setPositiveButton("Retry", null);
                builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
                builder.show();

            }
        }

        if (flag.equals("dlFriendList")) {
            logger.frndlst=result;
            MapsActivity.pdMarkers.dismiss();
        }
        if (flag.equals("dlMarkers")) {
             logger.markers=result;
            MapsActivity.pdMarkers2.dismiss();
        }
        if (flag.equals("dlAll")) {
            // dummy.markers=result;
            allPeople.name=result;
            allPeople.pdMarkers.dismiss();
        }
        if (flag.equals("sendRqst")) {
            // dummy.markers=result;
            if(result.equals("done")){Toast.makeText(contex, "Request sent", Toast.LENGTH_SHORT).show();}
            else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}
        }
        if (flag.equals("delFrnd")) {
            // dummy.markers=result;
            if(result.equals("done")){Toast.makeText(contex, "Friend deleted", Toast.LENGTH_SHORT).show();}
            else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}
        }
        if (flag.equals("acceptRqst")) {
            // dummy.markers=result;
            if(result.equals("done")){Toast.makeText(contex, "Friend added", Toast.LENGTH_SHORT).show();
            MapsActivity.updateRequired=true;
            }
            else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}
        }
        if (flag.equals("rejectRqst")) {
            // dummy.markers=result;
            if(result.equals("done")){Toast.makeText(contex, "Request Removed", Toast.LENGTH_SHORT).show();}
            else{Toast.makeText(contex, "Error :"+result, Toast.LENGTH_SHORT).show();}
        }
        if (flag.equals("changeAvatar")) {
            // dummy.markers=result;
            if(result.equals("done")){Toast.makeText(contex, "Avatar Changed", Toast.LENGTH_SHORT).show();}
            else{Toast.makeText(contex, "Error :"+result, Toast.LENGTH_SHORT).show();}
        }
        if (flag.equals("searchRqst")) {
            logger.rqsts=result;
           //if(result.equals("")){Toast.makeText(contex, "NO Request found", Toast.LENGTH_SHORT).show();}
           //else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}
            notifications.pdMarkersRqst.dismiss();
        }
        if (flag.equals("register")) {
            signup.message=result;
            //if(result.equals("done")){Toast.makeText(contex, "Request Removed", Toast.LENGTH_SHORT).show();}
            // else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}
            signup.pd.dismiss();
        }
        if (flag.equals("uploadImage")) {

            if(result.equals("done")){Toast.makeText(contex, "Image Uploaded", Toast.LENGTH_SHORT).show();}
             else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}

        }
        if (flag.equals("downloadImage")) {

            if(result.equals("done")){


                Toast.makeText(contex, "Image Uploaded", Toast.LENGTH_SHORT).show();


            }
            else{Toast.makeText(contex, "ERROR", Toast.LENGTH_SHORT).show();}

        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

