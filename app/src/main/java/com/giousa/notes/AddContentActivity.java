package com.giousa.notes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
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
    private int mFlag;
    private NotesDB mNotesDB;
    private SQLiteDatabase mDatabaseWriter;
    private File mPhoneFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);

        mFlag = getIntent().getIntExtra("flag",0);
        initDatabase();
        initView();
    }

    private void initDatabase() {
        mNotesDB = new NotesDB(this);
        mDatabaseWriter = mNotesDB.getWritableDatabase();

    }


    private void initView(){

        switch (mFlag){

            case 1:
                System.out.println("---1---");
                mImg.setVisibility(View.GONE);
                mVideo.setVisibility(View.GONE);
                break;

            case 2:
                System.out.println("---2---");
                mImg.setVisibility(View.VISIBLE);
                mVideo.setVisibility(View.GONE);
                Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mPhoneFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"+getTime()+".jpg");
                intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoneFile));
                startActivityForResult(intentImage,1);
                break;

            case 3:
                System.out.println("---3---");

                mImg.setVisibility(View.GONE);
                mVideo.setVisibility(View.VISIBLE);
                break;

        }

    }

    private void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, mEditText.getText().toString());
        cv.put(NotesDB.TIME, getTime());
        cv.put(NotesDB.PATH, mPhoneFile+"");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult");

        switch (requestCode){

            case 1:
                System.out.println("-------onActivityResult-----");
                Bitmap bitmap = BitmapFactory.decodeFile(mPhoneFile.getAbsolutePath());
                mImg.setImageBitmap(bitmap);
                break;

            case 2:

                break;

        }
    }
}
