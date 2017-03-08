package com.giousa.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.list)
    ListView mList;
    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseWriter;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        initDatabase();

    }

    private void initDatabase() {
        mNotesDB = new NotesDB(this);
        mDatabaseWriter = mNotesDB.getWritableDatabase();
        addDB();
    }

    private void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, "Hello");
        cv.put(NotesDB.TIME, getTime());
        mDatabaseWriter.insert(NotesDB.TABLE_NAME, null, cv);
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    @OnClick({R.id.btn_text, R.id.btn_img, R.id.btn_video})
    public void onClick(View view) {

        mIntent = new Intent(this,AddContentActivity.class);

        switch (view.getId()) {
            case R.id.btn_text:
                mIntent.putExtra("flag","1");
                startActivity(mIntent);
                break;
            case R.id.btn_img:
                mIntent.putExtra("flag","2");
                startActivity(mIntent);
                break;
            case R.id.btn_video:
                mIntent.putExtra("flag","3");
                startActivity(mIntent);
                break;
        }
    }
}
