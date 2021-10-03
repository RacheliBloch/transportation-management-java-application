package com.example.myapplication2.UI.ui.CompanyTravels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication2.Data.TravelRepository;
import com.example.myapplication2.Entities.Travel;

import java.util.ArrayList;
import java.util.List;

public class CompanyTravelsModel extends ViewModel {
    static TravelRepository rep;
    public CompanyTravelsModel(Context context) { rep=new TravelRepository(context); }
    public ArrayList<Travel> GetTravelOfUser(String mail) { return   rep.GetTravelOfUser(mail); }

    public static ArrayList<Travel>  GetSuitableTravels(double curLatitude, double curLongitude, Context context,Double chosendistance)
    { ArrayList<Travel> l=rep.GetSuitableTravels(curLatitude,curLongitude, context, chosendistance); return l; }

    public void UpdeteTravel(Travel updatedtravel) { rep.UpdateTravel(updatedtravel); }

}