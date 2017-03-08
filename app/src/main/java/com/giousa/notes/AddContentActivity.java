package com.giousa.notes;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/8
 * Time:上午10:33
 */

public class AddContentActivity extends Activity {

    @InjectView(R.id.c_img)
    ImageView mImg;
    @InjectView(R.id.c_video)
    VideoView mVideo;
    @InjectView(R.id.ettext)
    EditText mEditText;
    private String mFlag;
    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);

        mFlag = getIntent().getStringExtra("flag");
        System.out.println("flag = " + mFlag);

        initDatabase();
    }

    private void initDatabase() {
        mNotesDB = new NotesDB(this);
        mDatabaseWriter = mNotesDB.getWritableDatabase();

    }

    private void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, mEditText.getText().toString());
        cv.put(NotesDB.TIME, getTime());
        cv.put(NotesDB.PATH, "path");
        cv.put(NotesDB.VIDEO, "video");
        mDatabaseWriter.insert(NotesDB.TABLE_NAME, null, cv);
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    @OnClick({R.id.save, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.delete:
                finish();
                break;
        }
    }
}
