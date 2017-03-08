package com.giousa.notes;

import android.content.Context;
import android.database.Cursor;
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
        contentv.setText(content);
        timetv.setText(time);

        return mLayout;
    }
}
