package com.example.myapplication2.UI.ui.HistoryTravels;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication2.Data.HistoryDataSource;
import com.example.myapplication2.Data.TravelRepository;
import com.example.myapplication2.Entities.Travel;

import java.util.ArrayList;

public class HistoryTravelsModel extends ViewModel {

    TravelRepository rep;

    public HistoryTravelsModel(Context context) { rep =new TravelRepository(context); }


    public ArrayList<Travel> ConvertToList(HistoryDataSource historyDataSource, FragmentActivity activity) {
        return  rep.ConvertToList(historyDataSource,activity);
    }
}