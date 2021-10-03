package com.example.myapplication2.Data;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.Entities.UserLocation;
import com.example.myapplication2.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TravelRepository {
    protected TravelDataSource dataBase = TravelDataSource.getInstance();
    MutableLiveData<List<Travel>> livedata=new MutableLiveData<List<Travel>>();
    HistoryDataSource historyDataSource;
    private static TravelRepository instance;
    public static TravelRepository getInstance(Context application) {
        if (instance == null)
            instance = new TravelRepository(application);
        return instance;
    }

    public TravelRepository(Context context) {
        historyDataSource=HistoryDataSource.getInstance(context);

    }

    public MutableLiveData<List<Travel>> getLivedata() {
        return livedata;
    }

    public TravelDataSource getDataBase() {
        return dataBase;
    }


    public ArrayList<Travel> GetTravelOfUser(String mail)
    {
        ArrayList<Travel> mytravels=new ArrayList<Travel>();
       List<Travel> all=dataBase.getAllTravels();
        for (Travel t :all) {
            String g=t.getClientEmail();
            if(t.getClientEmail().equals(mail))
                mytravels.add(t);
        }
        return mytravels;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng LatLan= null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            LatLan= new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return LatLan;
    }



    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    public float calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));

    }


    public ArrayList<Travel>  GetSuitableTravels(double curLatitude, double curLongitude,Context context,Double chosendistance)
    {

        ArrayList<Travel> suitabletravels=new ArrayList<Travel>() {};
        List<Travel> all=dataBase.getAllTravels();
        for (int i=0;i<all.size();i++) {
            UserLocation d=all.get(i).getUs();
            float distance=  calculateDistance(curLatitude,  curLongitude,d.getLat(),d.getLon());
          if(distance<chosendistance)
              suitabletravels.add(all.get(i));

        }
    






        return suitabletravels;

    }

    public void UpdateTravel(Travel updatetravel) {
        dataBase.updateTravel(updatetravel);
    }

    public ArrayList<Travel> ConvertToList(HistoryDataSource historyDataSource, FragmentActivity act) {
        LiveData<List<Travel>> J=historyDataSource.getTravels();
        ArrayList<Travel>Y=new ArrayList<Travel>();
        J.observe(act, new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                for (Travel t :
                        travels) {
                    Y.add(t);
                }
            }

        });
        return  Y;
    }

}
