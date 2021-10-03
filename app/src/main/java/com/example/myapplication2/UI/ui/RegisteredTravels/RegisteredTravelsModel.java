package com.example.myapplication2.UI.ui.RegisteredTravels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication2.Data.TravelRepository;
import com.example.myapplication2.Entities.Travel;

import java.util.ArrayList;

public class RegisteredTravelsModel extends ViewModel {

    static TravelRepository rep;
    public RegisteredTravelsModel(Context context) {
        rep=new TravelRepository(context);
    }

    public void UpdeteTravel(Travel uptra) {
        rep.UpdateTravel(uptra);
    }
    public ArrayList<Travel> GetTravelOfUser(String mail)
    {
        return   rep.GetTravelOfUser(mail);
    }
}