package com.example.myapplication2.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.R;
import com.example.myapplication2.UI.ui.CompanyTravels.CompanyTravelsModel;

import java.util.HashMap;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.TravelCompanyViewHolder> {
    private CompanyTravelsModel viewModel;
    private List<Travel> travels;
    Context context1;
    public List<Travel> getTravels(){
        return travels;
    }
    public void setTravels(List<Travel> l){
        this.travels=l;
    }
    public CompanyAdapter(List<Travel> l,Context context,String c){this.travels=l;
        viewModel=new CompanyTravelsModel(context);
        companyname=c;
    context1=context;}
    String companyname;


    @NonNull
    @Override
    public TravelCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.companytravels_item, parent, false);
        TravelCompanyViewHolder holder = new TravelCompanyViewHolder((view));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TravelCompanyViewHolder holder, int position) {
        if (travels == null)
            return;
        Travel travel = travels.get(position);

        holder.custname.setText("שם לקוח: "+travel.getClientName());
        holder.startaddress.setText("כתובת יציאה: "+travel.getUserLocation());
        holder.endaddress.setText("כתובת חזרה: "+travel.getTarget());
        holder.numt.setText("מספר נוסעים: "+Integer.toString(travel.getNumOfTravelers()));
        if(travel.getCompany()!=null){
            for (String company : travel.getCompany().keySet()) {
                if(company.equals(companyname)) {   holder.suggest.setEnabled(false); }
            }
        }
        try {
            Boolean b=travel.getCompany().get(companyname).booleanValue();
            if(b){ holder.submit.setChecked(true); } }
        catch (Exception e) { }


        if( travel.getCompany()==null)
            holder.suggest.setEnabled(true);

        holder. suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(travel.getCompany()==null){
                    HashMap<String,Boolean> temp=new HashMap<String, Boolean>();
                    temp.put(companyname,false);
                    travel.setCompany(temp );
                    travel.setRequesType(Travel.RequestType.sent);}
                else
                    travel.getCompany().put(companyname,false);
                    travel.setRequesType(Travel.RequestType.sent);
                    viewModel.UpdeteTravel(travel);
                    Toast.makeText(context1,"the request has been sent",Toast.LENGTH_LONG).show();
                    holder. suggest.setEnabled(false);

            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:035555555"));
                startActivity(context1,callIntent,null);
            }
        });


        holder.sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to=travel.getClientEmail();
                String subject="suggest for you to travel from: "+companyname;
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(context1,intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (travels == null)
            return 0;
        else
            return travels.size();
    }

    public class TravelCompanyViewHolder extends RecyclerView.ViewHolder{
        TextView custname, startaddress, endaddress, numt,done;
        Button suggest;
        Button sendmail,call;
         CheckBox submit;
        View view;
        public TravelCompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            call=itemView.findViewById(R.id.callbtn);
            sendmail=itemView.findViewById(R.id.sendmailbtn);
            custname = (TextView) itemView.findViewById(R.id.custname);
            startaddress = (TextView) itemView.findViewById(R.id.startaddress);
            endaddress = (TextView) itemView.findViewById(R.id.endaddress);
             numt = (TextView) itemView.findViewById(R.id.numt);
            suggest= (Button)itemView.findViewById(R.id.approvebtn);
            submit= itemView.findViewById(R.id.checkBoxSubmit);
            //done=itemView.findViewById(R.id.done);
        }
    }
}
