package com.example.myapplication2.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.Data.HistoryDataSource;
import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.R;
import com.example.myapplication2.UI.ui.RegisteredTravels.RegisteredTravelsModel;

import java.util.Date;
import java.util.List;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisteredTravelViewHolder> {
    private RegisteredTravelsModel viewModel;
    private List<Travel> travels;
    Context context1;
    public List<Travel> getTravels(){
        return travels;
    }
    public void setTravels(List<Travel> l){
        this.travels=l;
    }
    public RegisterAdapter(List<Travel> l,Context context,String c){this.travels=l;
        viewModel=new RegisteredTravelsModel(context);
        companyname=c;
        context1=context;}
    String companyname;


    @NonNull
    @Override
    public RegisteredTravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytravels_item, parent, false);
        RegisteredTravelViewHolder holder = new RegisteredTravelViewHolder((view));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredTravelViewHolder holder, int position) {
        if (travels == null)
            return;
        Travel travel = travels.get(position);
        try {
            for (String key:travel.getCompany().keySet()) {
                if(travel.getCompany().get(key).equals(true))
                   holder. choosebtn.setEnabled(false);
                Travel.RequestType g=travel.getRequesType();
                if(g.equals(Travel.RequestType.close)){
                    holder.travelendedbtn.setEnabled(false); }
                break;
            }
        }
        catch (Exception e) { holder. choosebtn.setEnabled(true); }
        holder.travelendedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel.setRequesType(Travel.RequestType.close);
                viewModel.UpdeteTravel(travel);
                HistoryDataSource historyDataSource= HistoryDataSource.getInstance(context1);
                historyDataSource.addTravel(travel);
                holder.travelendedbtn.setEnabled(false);
            }
        });
        holder.choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.sc.getSelectedItem()==null){
                    Toast.makeText(context1,"לא נבחרה חברה",Toast.LENGTH_LONG).show();
                    return;
                }
                String company=holder.sc.getSelectedItem().toString();
                travel.getCompany().remove(company);
                travel.getCompany().put(company,true);
                viewModel.UpdeteTravel(travel);
                holder.choosebtn.setEnabled(false);
            }
        });

        ArrayAdapter<String > companies=new ArrayAdapter<String>(context1, android.R.layout.simple_spinner_item);
        if( travel.getCompany()!=null)
        {
            for (String key :travel.getCompany().keySet() )
            {
                companies.add(key);
            }}

       holder. sc.setAdapter(companies);
        Date V=travel.getTravelDate();
        Integer day=V.getDay();
        Integer month=V.getMonth();
        Integer year=V.getYear();

        holder.idstartdate.setText("תאריך יציאה: "+day.toString()+"/"+month.toString()+"/"+year.toString());
        Date end=travel.getArrivalDate();
        Integer eday=end.getDay();
        Integer emonth=end.getMonth();
        Integer eyear=end.getYear();

        holder.enddate.setText("תאריך חזרה: "+eday.toString()+"/"+emonth.toString()+"/"+eyear.toString());
        Integer nt=travel.getNumOfTravelers();
        holder.numt.setText("מספר נוסעים: "+nt.toString());
    }

    @Override
    public int getItemCount() {
        if (travels == null)
            return 0;
        else
            return travels.size();
    }

    public class RegisteredTravelViewHolder extends RecyclerView.ViewHolder{
        TextView idstartdate, enddate, numt;
        Spinner sc;
        Button choosebtn;
        Button travelendedbtn;
        //CheckBox submit;
        View view;
        public RegisteredTravelViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
             idstartdate = (TextView) itemView.findViewById(R.id.idstart);
             enddate = (TextView) itemView.findViewById(R.id.enddate);
             numt = (TextView) itemView.findViewById(R.id.numt);
             sc = (Spinner) itemView.findViewById(R.id.spinnercompanies);
             travelendedbtn=itemView.findViewById(R.id.travelsubmitend);
             choosebtn=itemView.findViewById(R.id.choosecompany);
        }
    }
}
