package com.example.a01.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myproject.BaseActivity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
