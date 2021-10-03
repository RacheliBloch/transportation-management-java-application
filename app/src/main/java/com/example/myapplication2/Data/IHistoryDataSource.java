package com.example.myapplication2.Data;

import com.example.myapplication2.Entities.Travel;

import java.util.List;

public interface IHistoryDataSource {
    public void addTravel(Travel p);

    public void addTravel(List<Travel> travelList);

    public void editTravel(Travel p);

    public void deleteTravel(Travel p);

    public void clearTable();
}
