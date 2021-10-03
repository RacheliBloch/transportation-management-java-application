package com.example.myapplication2.UI.ui.HistoryTravels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication2.Data.HistoryDataSource;
import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.UI.Activities.NavigationActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class HistoryTravelsFragment extends Fragment {

    private HistoryTravelsModel historyTravelsModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // historyTravelsModel =new ViewModelProvider(this).get(HistoryTravelsModel.class);
        HistoryDataSource historyDataSource= HistoryDataSource.getInstance(getContext());
        View root = inflater.inflate(R.layout.fragment_historytravels, container, false);
        LiveData<List<Travel>> endedtravelslive=historyDataSource.getTravels();
        ArrayList<Travel>endedtravels=new ArrayList<Travel>();
        NavigationActivity activity = (NavigationActivity) getActivity();
        String myDataFromActivity = activity.getMyData();
        if(myDataFromActivity.equals("tahelnadav@gmail.com"))
        {
            endedtravelslive.observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {

            @Override
            public void onChanged(List<Travel> travels) {
                endedtravels.clear();
                for (Travel t :
                        travels) {
                    endedtravels.add(t);
                }

                ArrayAdapter<Travel> T=new ArrayAdapter<Travel>(getActivity(),R.layout.history_view, endedtravels) {

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if (convertView == null) {

                            convertView = View.inflate(this.getContext(),
                                    R.layout.history_view, null);
                        }

                        if(endedtravels.size()!=0) {
                            Button sendmail=convertView.findViewById(R.id.sendmailtocompany);
                            sendmail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String subject="הודעה חדשה ממנהל אפליקציית Itravely ";
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                    startActivity(intent,null);
                                }
                            });
                            TextView name = convertView.findViewById(R.id.name);
                            name.setText("name: "+endedtravels.get(position).getClientName());
                            TextView numtravelers = convertView.findViewById(R.id.numtravels);
                            Integer numt=endedtravels.get(position).getNumOfTravelers();
                            numtravelers.setText("num of travelers: "+numt.toString());

                        }
                        return convertView;
                    }
                };
                if(endedtravels.size()!=0){
                    ListView lv=(ListView) root.findViewById(R.id.historyfrag) ;
                    lv.setAdapter(T);}
            }

        });
        }
        else{
           TextView permission= root.findViewById(R.id.checkpermission);
           permission.setVisibility(View.VISIBLE);
        }

        return root;
    }
}