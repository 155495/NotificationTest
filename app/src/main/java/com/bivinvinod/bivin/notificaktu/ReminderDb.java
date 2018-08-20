package com.bivinvinod.bivin.notificaktu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class ReminderDb extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="ktudata.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_REMINDERS = "ReminderTable";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    private static final String KEY_ACTIVE = "active";
    public ReminderDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  String getAll(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM ReminderTable",null);
        cursor.moveToNext();

      return cursor.getString(1);
    }

    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM ReminderTable",null);
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setID(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDate(cursor.getString(2));
                reminder.setTime(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4).toString());
                reminder.setRepeatNo(cursor.getString(5));
                reminder.setRepeatType(cursor.getString(6).toString());
                reminder.setActive(cursor.getString(7).toString());

                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }
    public Reminder getReminder(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return reminder;
    }
        public void turnOffReminder(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ACTIVE , "false");
            db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                    new String[]{String.valueOf(id)});

        }
}
