package com.giousa.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.list)
    ListView mList;
    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseReader;
    private Intent mIntent;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initDatabase();

    }

    private void initDatabase() {
        mNotesDB = new NotesDB(this);
        mDatabaseReader = mNotesDB.getReadableDatabase();
    }

    @OnClick({R.id.btn_text, R.id.btn_img, R.id.btn_video})
    public void onClick(View view) {

        mIntent = new Intent(this,AddContentActivity.class);

        switch (view.getId()) {
            case R.id.btn_text:
                mIntent.putExtra("flag",1);
                startActivity(mIntent);
                break;
            case R.id.btn_img:
                mIntent.putExtra("flag",2);
                startActivity(mIntent);
                break;
            case R.id.btn_video:
                mIntent.putExtra("flag",3);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    private void selectDB(){
        Cursor cursor = mDatabaseReader.query(NotesDB.TABLE_NAME, null, null, null,null,null,null);
        mMyAdapter = new MyAdapter(this,cursor);
        mList.setAdapter(mMyAdapter);
    }


}
