package com.giousa.notes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/8
 * Time:下午2:11
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private Cursor mCursor;
    private LinearLayout mLayout;

    public MyAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLayout = (LinearLayout) inflater.inflate(R.layout.cell,null);
        TextView contentv = (TextView) mLayout.findViewById(R.id.list_content);
        TextView timetv = (TextView) mLayout.findViewById(R.id.list_time);
        ImageView imgiv = (ImageView) mLayout.findViewById(R.id.list_img);
        ImageView videoiv = (ImageView) mLayout.findViewById(R.id.list_video);
        mCursor.moveToPosition(i);
        String content = mCursor.getString(mCursor.getColumnIndex(NotesDB.CONTENT));
        String time = mCursor.getString(mCursor.getColumnIndex(NotesDB.TIME));
        String imageUrl = mCursor.getString(mCursor.getColumnIndex(NotesDB.PATH));
        String videoUrl = mCursor.getString(mCursor.getColumnIndex(NotesDB.VIDEO));
        contentv.setText(content);
        timetv.setText(time);
        imgiv.setImageBitmap(getImageThumbnail(imageUrl,200,200));
        videoiv.setImageBitmap(getVideoThumbnail(videoUrl,200,200, MediaStore.Images.Thumbnails.MICRO_KIND));

        return mLayout;
    }

    private Bitmap getImageThumbnail(String uri,int width,int height){

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri,options);
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth/width;
        int beHeight = options.outHeight/height;
        int be = 1;
        if(beWidth < beHeight){
            be = beWidth;
        }else{
            be = beHeight;
        }

        if(be <= 0){
            be = 1;
        }

        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(uri,options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;
    }

    private Bitmap getVideoThumbnail(String uri,int width,int height,int kind){

        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(uri,kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
