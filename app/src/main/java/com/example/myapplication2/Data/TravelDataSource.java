package com.example.myapplication2.Data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication2.Entities.Travel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class TravelDataSource implements ITravelDataSource{

    MutableLiveData<Boolean> isSuccess = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travels = database.getReference("travels");
    private NotifyToTravelListListener notifyToTravelListListener;
    private List<Travel> allTravelsList;
    private static TravelDataSource instance;

    public static TravelDataSource getInstance() {
        if (instance == null)
            instance = new TravelDataSource();
        return instance; }


    @Override
    public void addTravel(Travel p) {
        String id = travels.push().getKey();
        p.setTravelId(id);
        travels.child(id).setValue(p).addOnSuccessListener(aVoid -> isSuccess.postValue(true));
    }

    public  void removeTravel(String id) {
        travels.child(id).removeValue().addOnSuccessListener(aVoid -> isSuccess.postValue(true));
    }
    @Override
    public void updateTravel(final Travel toUpdate) {
        removeTravel(toUpdate.getTravelId());
        addTravel(toUpdate);
    }
    @Override
    public List<Travel> getAllTravels() {
        return allTravelsList;
    }

  @Override
   public void setNotifyToTravelListListener(NotifyToTravelListListener l){  notifyToTravelListListener = l;}


    public TravelDataSource()
    {
        allTravelsList = new ArrayList<>();
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allTravelsList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Travel travel = snapshot.getValue(Travel.class);
                        allTravelsList.add(travel);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



}
