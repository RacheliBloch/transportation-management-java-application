package com.example.myapplication2.Data;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication2.Entities.Travel;

import java.util.List;

public class HistoryDataSource implements IHistoryDataSource{
    private TravelDao travelDao;
   static HistoryDataSource historyDataSource;

    public HistoryDataSource(Context context){
        RoomDataSource database= RoomDataSource.getInstance(context);
        travelDao =database.getTravelDao();
    }
    public static HistoryDataSource getInstance(Context context)
    {
        if (historyDataSource == null)
            historyDataSource = new HistoryDataSource(context);
        return historyDataSource;
    }


    public LiveData<List<Travel>> getTravels(){
        return travelDao.getAll();
    }

    public LiveData<Travel> getTravel(String id){
        return travelDao.get(id);
    }

    public void addTravel(Travel p) {
        travelDao.insert(p);
    }

    public void addTravel(List<Travel> travelList) {
        travelDao.insert(travelList);
    }

    public void editTravel(Travel p) {
        travelDao.update(p);
    }

    public void deleteTravel(Travel p){
        travelDao.delete(p);
    }

    public void clearTable(){travelDao.clear();}


}

