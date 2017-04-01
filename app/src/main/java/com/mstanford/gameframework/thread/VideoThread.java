package com.mstanford.gameframework.thread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.example.myproject.BootActivity;
import com.example.myproject.VideoViewSubtitle;

/**
 * Created by 01 on 2017/3/31.
 */

public class VideoThread extends AsyncTask {
    private boolean isPaused=true;
    private Context cx;
    private long lStartTime;
    private long lCurentTime;

    public VideoThread(Context cx){
        isPaused=false;
        this.cx=cx;
        lStartTime= SystemClock.currentThreadTimeMillis();

    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isPaused = false;
        Intent intent = (Intent) new Intent(cx, VideoViewSubtitle.class);
        cx.startActivity(intent);
    }
}
