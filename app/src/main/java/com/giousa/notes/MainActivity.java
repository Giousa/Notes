package com.giousa.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotesDB = new NotesDB(this);
        mDatabaseWriter = mNotesDB.getWritableDatabase();
        addDB();
    }

    private void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,"Hello");
        cv.put(NotesDB.TIME,getTime());
        mDatabaseWriter.insert(NotesDB.TABLE_NAME,null,cv);
    }

    private String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
}
