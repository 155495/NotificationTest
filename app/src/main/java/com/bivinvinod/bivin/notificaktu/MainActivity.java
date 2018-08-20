package com.bivinvinod.bivin.notificaktu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String mRepeat;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;
    private long mRepeatTime;

    private Calendar mCalendar =Calendar.getInstance();

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        TextView txtHeadingCalendar=findViewById(R.id.heading);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font2.otf");
        txtHeadingCalendar.setTypeface(typeface,Typeface.BOLD);
          //final Context context=this;
       /* Stetho.newInitializerBuilder(context)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build();
        Stetho.initializeWithDefaults(this);
        /*OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();*/

        /*ShortcutManager shortcutManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                shortcutManager = getSystemService(ShortcutManager.class);



            ShortcutInfo shortcut ,shortcutInfo= null;

                shortcut = new ShortcutInfo.Builder(this, "id1")
                    .setShortLabel("Web site")
                    .setIcon(Icon.createWithResource(context, android.R.drawable.btn_minus))
                    .setIntent(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/")))
                    .build();


            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
    }}*/
        LinearLayout linearLayout=findViewById(R.id.linearLayout4);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CalendarActivity.class));
            }
        });

        LinearLayout linearLayoutSyllabus=findViewById(R.id.linearLayout5);
        linearLayoutSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Available in Next Update..",Toast.LENGTH_SHORT).show();
                   }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("Ktu Event Notification", false)) {

                    doAll();



            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        else {
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
        }






    }


public void doAll(){
    ReminderDb reminderDb=new ReminderDb(getApplicationContext());
    Log.d("ktudatabase",reminderDb.getAll()) ;
   // Toast.makeText(getApplicationContext(),reminderDb.getAll(),Toast.LENGTH_LONG).show();

        /*Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                0, pendingIntent);
         pendingIntent = PendingIntent.getBroadcast
                (this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis()+99000,
                10000, pendingIntent);*/


    List<Reminder> reminders=reminderDb.getAllReminders();
    for(Reminder rm : reminders){
        mReceivedID = rm.getID();
        mRepeat = rm.getRepeat();
        mRepeatNo = rm.getRepeatNo();
        mRepeatType = rm.getRepeatType();
        mActive = rm.getActive();
        mDate = rm.getDate();
        mTime = rm.getTime();

        mDateSplit = mDate.split("/");
        mTimeSplit = mTime.split(":");


        mDay = Integer.parseInt(mDateSplit[0]);
        mMonth = Integer.parseInt(mDateSplit[1]);
        mYear = Integer.parseInt(mDateSplit[2]);
        mHour = Integer.parseInt(mTimeSplit[0]);
        mMinute = Integer.parseInt(mTimeSplit[1]);

        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }


        if (mActive.equals("true")) {
            if (mRepeat.equals("true")) {
                setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedID, mRepeatTime);
            } else if (mRepeat.equals("false")) {
                //setAlarm(context, mCalendar, mReceivedID);
            }
        }
    }
}

    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
        AlarmManager mAlarmManager;
        PendingIntent mPendingIntent;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("id", Integer.toString(ID));
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification timein
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using initial notification time and repeat interval time
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime, mPendingIntent);

        // Restart alarm if device is rebooted
        //
        /*
        //ComponentName receiver = new ComponentName(context, BootReceiver.class);
        //PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/
    }

    /*private static class SampleDumperPluginsProvider implements DumperPluginsProvider {
        private final Context mContext;
        public SampleDumperPluginsProvider(Context context) {

            mContext = context;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            ArrayList<DumperPlugin> plugins=new ArrayList<>();
            for(DumperPlugin defaultPlugin :Stetho.defaultDumperPluginsProvider(mContext).get()){
                plugins.add(defaultPlugin);
            }
            plugins.add(new Helloworlddumbpelogugn());

            return plugins;
        }
    }*/
}
