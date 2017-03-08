package com.giousa.notes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/8
 * Time:下午3:50
 */

public class SelectActivity extends Activity {

    @InjectView(R.id.s_img)
    ImageView mImage;
    @InjectView(R.id.s_video)
    VideoView mVideo;
    @InjectView(R.id.s_tv)
    TextView mTextView;

    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.inject(this);

        getIntent().getIntExtra(NotesDB.ID, 0);

        initDatabase();

        initView();
    }

    private void initDatabase() {
        mNotesDB = new NotesDB(this);
        mDatabaseWriter = mNotesDB.getWritableDatabase();
    }


    private void initView() {

        String imagePath = getIntent().getStringExtra(NotesDB.PATH);
        String videoPath = getIntent().getStringExtra(NotesDB.VIDEO);

        System.out.println("-----"+imagePath);
        System.out.println("-----"+videoPath);

        if(imagePath.equals("null")){
            mImage.setVisibility(View.GONE);
        }else{
            mImage.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mImage.setImageBitmap(bitmap);
        }

        if(videoPath.equals("video") || videoPath.equals("null")){
            mVideo.setVisibility(View.GONE);
        }else{
            mVideo.setVisibility(View.VISIBLE);
            mVideo.setVideoURI(Uri.parse(videoPath));
            mVideo.start();
        }

        mTextView.setText(getIntent().getStringExtra(NotesDB.CONTENT));
    }


    @OnClick({R.id.s_delete, R.id.s_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.s_delete:
                deleteData();
                finish();
                break;
            case R.id.s_back:
                finish();
                break;
        }
    }

    private void deleteData() {
        mDatabaseWriter.delete(NotesDB.TABLE_NAME,NotesDB.ID+"="+getIntent().getIntExtra(NotesDB.ID,0),null);
    }
}
