package com.example.saad.whereru;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    public static int userID=-1;
    static ProgressDialog pdMarkersLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        //MapsActivity.p=null;
        //Toast.makeText(getApplicationContext(),"Nulled",Toast.LENGTH_LONG).show();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.

        final TextView signUp=(TextView)findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(Login.this,signup.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

        retrieveLoginData();
        //destryLoginData();
       if(userID!=-1){

           Intent myIntent = new Intent(Login.this, MapsActivity.class);
           myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(myIntent);


       }



        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user=(EditText)findViewById(R.id.email);
                EditText pass=(EditText)findViewById(R.id.password);
                checkLoginData(user.getText()+"",pass.getText()+"");

            }
        });

    }



    private void checkLoginData(String user,String pass) {
        logger l=new logger (this,this,"asd");
        l.execute("login",user,pass);

        pdMarkersLogin = new ProgressDialog(Login.this);
        pdMarkersLogin.setTitle("Logging in");
        pdMarkersLogin.setMessage("Please wait");
        pdMarkersLogin.setCancelable(false);
        pdMarkersLogin.show();
        pdMarkersLogin.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                CheckBox chk=(CheckBox)findViewById(R.id.checkedTextView);
                if(chk.isChecked()){keepmeLoggedIn();}
                else{destryLoginData();}
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    private void retrieveLoginData(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.userID = preferences.getInt("ID",-1);

    }

    private void destryLoginData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID",-1);
        editor.apply();
    }
private void keepmeLoggedIn(){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt("ID",Login.userID);
    editor.apply();


}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
