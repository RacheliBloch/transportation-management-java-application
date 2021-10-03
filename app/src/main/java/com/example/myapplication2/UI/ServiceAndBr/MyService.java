package com.example.myapplication2.UI.ServiceAndBr;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication2.Entities.Travel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Provider;

public class MyService extends Service {
    Integer sum = 0;
    boolean isThreadOn = false;
    public final String TAG = "myService";
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travels = database.getReference("travels");

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Intent intent=new Intent("NEWTRAVEL");
                sendBroadcast(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isThreadOn)
        sum=0;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public class SumCalc extends Thread {

        public void run() {
            sum = 0;
            for(Integer idx = 0; idx< 10099; idx ++)
            {
                sum++;
            }
            isThreadOn = false;
        }
    }
}
