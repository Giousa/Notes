package com.giousa.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initDatabase();

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mCursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this,SelectActivity.class);
                i.putExtra(NotesDB.ID,mCursor.getInt(mCursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT,mCursor.getString(mCursor.getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,mCursor.getString(mCursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,mCursor.getString(mCursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,mCursor.getString(mCursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });

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
        mCursor = mDatabaseReader.query(NotesDB.TABLE_NAME, null, null, null,null,null,null);
        mMyAdapter = new MyAdapter(this, mCursor);
        mList.setAdapter(mMyAdapter);
    }


}
