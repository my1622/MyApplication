package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 01 on 2017/3/31.
 */

public class BroadcastBaseActivity extends BaseActivity {
    public static final String action = "jason.broadcast.action";
    private String what;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onBroadCast(String what)
    {
        this.what=what;
        //handler.sendEmptyMessage(what);
        Intent intent = new Intent(action);
        intent.putExtra("data", what);
        sendBroadcast(intent);
    }
}
