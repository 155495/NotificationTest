package com.bivinvinod.bivin.notificaktu.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bivinvinod.bivin.notificaktu.R;
import com.bivinvinod.bivin.notificaktu.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{
    static List<Reminder>dbList;
    static Context context;
    public CalendarAdapter(Context context, List<Reminder> dbList) {
        CalendarAdapter.dbList =new ArrayList<Reminder>();
        CalendarAdapter.context =context;
        CalendarAdapter.dbList =dbList;
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendercard,null);
        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //date to day conversion code ...........................................................................
        String input_date=dbList.get(position).getDate();
        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(dt1);
        //date to day conversion code ends...........................................................................

        Boolean status= Boolean.valueOf(dbList.get(position).getActive());
        if(status){
            holder.txtActive.setText("Waiting.....");
            holder.txtActive.setTextColor(Color.parseColor("#5FBA7D"));
        }
        else {
            holder.txtActive.setText("Notified...");
            holder.txtActive.setTextColor(Color.parseColor("#EA4335"));
        }


        holder.txtid.setText(""+( dbList.get(position).getID()));
        holder.txtEvent.setText(dbList.get(position).getTitle());
        holder.txtdate.setText(dbList.get(position).getDate());
        holder.txtDay.setText(finalDay);

        holder.gifSharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Notifica");
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Notifica KTU Notification:\n *"+dbList.get(position).getTitle()+
                                "* \n"+dbList.get(position).getDate()+"\n https://play.google.com/store/apps/details?id=com.bivinvinod.notificaktu");
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share Event"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    //**********************************************************************************************************************************
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtid;
        public TextView txtEvent;
        public TextView txtDay;
        public TextView txtdate;
        public TextView txtActive;
        public LinearLayout gifSharebtn;

        public ViewHolder(View itemView) {
            super(itemView);
            txtid=itemView.findViewById(R.id.txtid);
            txtEvent=itemView.findViewById(R.id.txtEvent);
            txtdate=itemView.findViewById(R.id.txtDate);
            txtDay=itemView.findViewById(R.id.txtDay);
            txtActive=itemView.findViewById(R.id.txtActive);
            gifSharebtn=itemView.findViewById(R.id.idgifshare);


        }

    }
    //**********************************************************************************************************************************
}
