package com.example.myapplication2.UI.ui.CompanyTravels;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.UI.Activities.NavigationActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.UI.Adapters.CompanyAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyTravelsFragment extends Fragment {

    LocationManager locationManager;
    LocationListener locationListener;
    double curLatitude, curLongitude;
    private CompanyTravelsModel companyTravelsModel;
    ArrayList<Travel> ourtravels = new ArrayList<Travel>();
   String companyname;



    protected CompanyAdapter travelsAdapter;
    protected RecyclerView listTravels;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//      companyTravelsModel = new ViewModelProvider(this).get(CompanyTravelsModel.class);
      companyTravelsModel = new CompanyTravelsModel(getContext());

        View root = inflater.inflate(R.layout.company_fragment, container, false);

        NavigationActivity activity = (NavigationActivity) getActivity();
        String myDataFromActivity = activity.getMyData();
        int iend = myDataFromActivity.indexOf("@");
         companyname = myDataFromActivity.substring(0, iend);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // return TODO;
        }
        Location location ;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        ArrayList<Travel> suitabelTravels =new ArrayList<>();
        ArrayList<Travel>  sTravels = CompanyTravelsModel.GetSuitableTravels(location.getLatitude(), location.getLongitude(), getContext(),1000.0);
        suitabelTravels= sortsutable(sTravels);
        Button filter= (Button) root.findViewById(R.id.filterbtn);
        EditText km=root.findViewById(R.id.editkm);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!km.getText().toString().matches(""))
                {
                  Double distance=  Double.parseDouble(km.getText().toString());
                    ArrayList<Travel>  sTravels = CompanyTravelsModel.GetSuitableTravels(location.getLatitude(), location.getLongitude(), getContext(),distance);
                    ArrayList<Travel> suitabelTravels=sortsutable(sTravels);
                    listTravels = root.findViewById(R.id.rec);
                    listTravels.setLayoutManager(new LinearLayoutManager(getContext()));
                    travelsAdapter = new CompanyAdapter(suitabelTravels,getContext(),companyname);
                    listTravels.setAdapter(travelsAdapter);

                }
                else {
                    Toast.makeText(getContext(),"בחר מרחק רצוי",Toast.LENGTH_LONG).show();
                }
            }
        });

        listTravels = root.findViewById(R.id.rec);
        listTravels.setLayoutManager(new LinearLayoutManager(getContext()));
        travelsAdapter = new CompanyAdapter(suitabelTravels,getContext(),companyname);
        listTravels.setAdapter(travelsAdapter);
        return root;
    }


    public  ArrayList<Travel> sortsutable(ArrayList<Travel> sTravels){
        ArrayList<Travel> suitabelTravels=new ArrayList<Travel>();
        for (Travel t : sTravels) {

            Boolean appruve=false;
            if(t.getCompany()!=null){
                for (String company : t.getCompany().keySet()) {
                    if ((t.getCompany().get(company))&&!company.equals(companyname)) {
                        appruve = true;
                        break; }
                }}
            if(appruve==false)
                suitabelTravels.add(t);

        }
        return suitabelTravels;

    }



    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
        }

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


    //getLocation From Address
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


}