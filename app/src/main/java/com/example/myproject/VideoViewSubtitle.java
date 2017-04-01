/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a01.myapplication.R;


import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnTimedTextListener;
import io.vov.vitamio.widget.VideoView;

public class VideoViewSubtitle extends BroadcastBaseActivity{
	//private static MyHandler handler;
	private String path = Environment.getExternalStorageDirectory().getPath()+"/love.mp4";;
	private String subtitle_path = "";
	private VideoView mVideoView;
	private TextView mSubtitleView;
	private long mPosition = 0;
	private int mVideoLayout = 0;
	public static final String action = "jason.broadcast.action";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.subtitle2);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mSubtitleView = (TextView) findViewById(R.id.subtitle_view);

		if (path == "") {
			// Tell the user to provide a media file URL/path.
			Toast.makeText(VideoViewSubtitle.this, "Please edit VideoViewSubtitle Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
			return;
		} else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			mVideoView.setVideoPath(path);

			// mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();

			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
					mVideoView.addTimedTextSource(subtitle_path);
					mVideoView.setTimedTextShown(true);
					mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);

				}
			});
			mVideoView.setOnTimedTextListener(new OnTimedTextListener() {

				@Override
				public void onTimedText(String text) {
					mSubtitleView.setText(text);
				}

				@Override
				public void onTimedTextUpdate(byte[] pixels, int width, int height) {

				}
			});
			mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
//					mediaPlayer.seekTo(0);   //转到第一帧
//					mediaPlayer.start();     //开始播放
					String what="finish";
					//handler.sendEmptyMessage(what);
					onBroadCast(what);
					finish();
				}
			});
		}
	}

	@Override
	protected void onPause() {
		mPosition = mVideoView.getCurrentPosition();
		mVideoView.stopPlayback();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mPosition > 0) {
			mVideoView.seekTo(mPosition);
			mPosition = 0;
		}
		super.onResume();
		mVideoView.start();
	}

	public void changeLayout(View view) {
		mVideoLayout++;
		if (mVideoLayout == 4) {
			mVideoLayout = 0;
		}
		switch (mVideoLayout) {
		case 0:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_100);
			break;
		case 1:
			mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_fit);
			break;
		case 2:
			mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_size);
			break;
		case 3:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_crop);

			break;
		}
		mVideoView.setVideoLayout(mVideoLayout, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode){
		case KeyEvent.KEYCODE_BACK:

            new AlertDialog.Builder(VideoViewSubtitle.this)
                    .setTitle("确定退出播放吗？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String what="back";
                            onBroadCast(what);
                            finish();

                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

			break;
		default:
		break;
		}
        return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}


	@Override
	public void onBroadCast(String what) {
		super.onBroadCast(what);
	}
}
