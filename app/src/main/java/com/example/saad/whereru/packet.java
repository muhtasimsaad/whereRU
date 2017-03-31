package com.example.saad.whereru;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SAAD on 03-07-2017.
 */

public class packet {
    public static String frndlst="";
    public static String markers="";
    ArrayList<friend> frnds=new  ArrayList<friend>();
    int id;
    public packet(int i,ArrayList<friend> ls){

        frnds=ls;
        id=i;

    }
    public packet(){id=-1;}
}



class friend{
    String name;
    LatLng position;
    Long time;
    int id;
    int imageID;
    public friend(String n,LatLng p,int i,int ii,Long d){
        time=d;
        name=n;
        position=p;
        imageID=ii;
        id=i;
    }
    public friend(String n,int i,int ii){

        name=n;
imageID=ii;
        id=i;
    }

}