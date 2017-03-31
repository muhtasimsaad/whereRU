package com.example.saad.whereru;





import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class nav_mother extends AppCompatActivity {


    final Fragment fragment1 = new myFriends();
    final Fragment fragment2 = new allPeople();
    final Fragment fragment3 = new profile();
    final Fragment fragment4 = new notifications();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.friends:
                    final FragmentManager fragmentManager = getSupportFragmentManager();

                   try {
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.contentss, fragment1).commit();
                       return true;

                   }
                   catch (Exception rr){
                       Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}
                    return true;
                case R.id.search:
                    final FragmentManager fragmentManager2 = getSupportFragmentManager();

                    try {
                        FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                        fragmentTransaction.replace(R.id.contentss, fragment2).commit();
                        return true;

                    }
                    catch (Exception rr){
                        Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}

                    return true;
                case R.id.profile:
                    final FragmentManager fragmentManager3 = getSupportFragmentManager();

                    try {
                        FragmentTransaction fragmentTransaction = fragmentManager3.beginTransaction();
                        fragmentTransaction.replace(R.id.contentss, fragment3).commit();
                        return true;

                    }
                    catch (Exception rr){
                        Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}

                    return true;
                case R.id.notifications:
                    final FragmentManager fragmentManager4 = getSupportFragmentManager();

                    try {
                        FragmentTransaction fragmentTransaction = fragmentManager4.beginTransaction();
                        fragmentTransaction.replace(R.id.contentss, fragment4).commit();
                        return true;

                    }
                    catch (Exception rr){
                        Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_mother);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentss, fragment1).commit();


        }
        catch (Exception rr){
            Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}



    }
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i=new Intent(nav_mother.this,MapsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
