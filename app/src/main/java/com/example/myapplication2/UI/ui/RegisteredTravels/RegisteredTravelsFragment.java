package com.example.myapplication2.UI.ui.RegisteredTravels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.UI.Activities.NavigationActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.UI.Adapters.RegisterAdapter;


import java.util.ArrayList;

public class RegisteredTravelsFragment extends Fragment {

    private RegisteredTravelsModel registeredTravelsModel;
    protected RecyclerView listTravels;
    protected RegisterAdapter travelsAdapter;
    ArrayList<Travel> mytravels=new ArrayList<Travel>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //registeredTravelsModel = new ViewModelProvider(this).get(RegisteredTravelsModel.class);
        registeredTravelsModel=new RegisteredTravelsModel(getContext());
        View root = inflater.inflate(R.layout.layoutregistered, container, false);
        NavigationActivity activity = (NavigationActivity) getActivity();
        String myDataFromActivity = activity.getMyData();
        mytravels= registeredTravelsModel.GetTravelOfUser(myDataFromActivity);
        listTravels = root.findViewById(R.id.recregister);
        listTravels.setLayoutManager(new LinearLayoutManager(getContext()));
        travelsAdapter = new RegisterAdapter(mytravels,getContext(),myDataFromActivity);
        listTravels.setAdapter(travelsAdapter);
        return root;
    }
}