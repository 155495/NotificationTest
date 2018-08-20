package com.bivinvinod.bivin.notificaktu;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import  com.bivinvinod.bivin.notificaktu.adapters.CalendarAdapter;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    ReminderDb  databaseOpenHelper;
    RecyclerView recyclerView;
    RecyclerView.Adapter reAdapter;
    RecyclerView.LayoutManager reLayoutManager;
    android.support.design.widget.FloatingActionButton fabback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        TextView txtHeadingCalendar=findViewById(R.id.txcalenderheading);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font2.otf");
        txtHeadingCalendar.setTypeface(typeface,Typeface.BOLD);


        databaseOpenHelper=new ReminderDb(this);
        List<Reminder>dbList=new ArrayList<Reminder>();
        dbList=databaseOpenHelper.getAllReminders();
        recyclerView=findViewById(R.id.calRecyclerview);
        recyclerView.setHasFixedSize(true);
        reLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter=new CalendarAdapter(this,dbList);
        recyclerView.setAdapter(reAdapter);
        Log.d("recycleSize", String.valueOf(dbList.size()));

        fabback=findViewById(R.id.fabcalenderback);
        fabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
