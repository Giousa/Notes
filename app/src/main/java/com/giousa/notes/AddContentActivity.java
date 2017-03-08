package com.giousa.notes;

import android.app.Activity;
import android.os.Bundle;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/8
 * Time:上午10:33
 */

public class AddContentActivity extends Activity {

    private String mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mFlag = getIntent().getStringExtra("flag");
        System.out.println("flag = "+mFlag);
    }
}
