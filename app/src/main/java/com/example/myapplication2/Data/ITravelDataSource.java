package com.example.myapplication2.Data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication2.Entities.Travel;

import java.util.List;

public interface ITravelDataSource {
    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();

    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(NotifyToTravelListListener l);
}
