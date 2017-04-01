package com.example.myproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a01.myapplication.MainActivity;
import com.example.a01.myapplication.R;
import com.mstanford.gameframework.thread.VideoThread;
import com.mstanford.gameframework.view.GameLoadingView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;


public class BootActivity extends BaseActivity {
	private ImageView bootImage, donttouchImageView;
	private LinearLayout backgroundLinLout;
	private MediaPlayer backGroundMusicmp;
	private SoundPool soundPool;
	static int ANIMATIONOVER = 0;
	private boolean isPlayed=false;
	private Vibrator vibrator;

	// private ImageView bootImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.activity_boot);
		soundPool = new SoundPool(2, 0, 5);
		soundPool.load(BootActivity.this, R.raw.wen, 1);
		// View v = null;
		// if (null==v){
		// v=(View)findViewById(R.layout.activity_boot);
		// }
		// setContentView(v);

		IntentFilter filter = new IntentFilter(VideoViewSubtitle.action);
		registerReceiver(broadcastReceiver, filter);

		backgroundLinLout = (LinearLayout) findViewById(R.id.backgroundImage);
		bootImage = (ImageView) findViewById(R.id.bootImage);
		donttouchImageView = (ImageView) findViewById(R.id.donttouch);

		Animation alf010 = new AlphaAnimation(0f, 1f);
		alf010.setDuration(2000);
		alf010.setRepeatMode(Animation.REVERSE);
		alf010.setRepeatCount(1);
		alf010.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO 自动生成的方法存根
				// Intent bootIntent=new
				// Intent(getApplicationContext(),BootOntouchActivity.class);
				// startActivity(bootIntent);
				// finish();
				Message message = new Message();
				message.what = ANIMATIONOVER;
				handler.sendMessage(message);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO 自动生成的方法存根

			}

		});
		bootImage.startAnimation(alf010);
		backgroundLinLout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		if (null != backGroundMusicmp && !isPlayed) {
			backGroundMusicmp.stop();
		}
	}

	@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		// 播放背景音乐
		if (null == backGroundMusicmp) {
			backGroundMusicmp = MediaPlayer.create(BootActivity.this,
					R.raw.backbootmusic);
		}
		backGroundMusicmp.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:

				backgroundLinLout.setGravity(Gravity.BOTTOM | Gravity.CENTER);
				backgroundLinLout.setBackgroundResource(R.drawable.timg);
				bootImage.setVisibility(View.GONE);
				Animation alf01 = new AlphaAnimation(0f, 1f);
				alf01.setDuration(2000);
				alf01.setRepeatMode(Animation.RESTART);
				alf01.setRepeatCount(Animation.INFINITE);
				donttouchImageView.startAnimation(alf01);
				break;
			case 1:
				vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
				long [] pattern = {100,400};   // 停止 开启 停止 开启
				vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
				View gameLoadingView = new GameLoadingView(BootActivity.this);
				setContentView(gameLoadingView);
				backGroundMusicmp.stop();
				//backGroundMusicmp.release();
				isPlayed=true;



				// 初始化soundPool,设置可容纳12个音频流，音频流的质量为5，


				// HashMap musicId=new HashMap();

				// musicId.put(1, soundPool.load(BootActivity.this, R.raw.click,
				// 1));

				// int play(int soundID, float leftVolume, float rightVolume,
				// int priority, int loop, float rate)：
				// 该方法的第一个参数指定播放哪个声音；
				// leftVolume、rightVolume指定左、右的音量：
				// priority指定播放声音的优先级，数值越大，优先级越高；
				// loop指定是否循环，0为不循环，-1为循环；
				// rate指定播放的比率，数值可从0.5到2， 1为正常比率。
				try {

					if (null != soundPool) {
						soundPool.play(1, 10, 10, 0, 0, 1);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}



				// soundPool.play(R.raw.windows,10,10, 0, -1, 1);
				// backGroundMusicmp.release();



				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		if (!isPlayed) {

			backGroundMusicmp.stop();
			backGroundMusicmp.release();
		}
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String  text=intent.getExtras().getString("data");
			if (text.equals("finish")||text.equals("back")){
				Log.v(this.getClass().getName(),"video_finish");
				Intent i = (Intent) new Intent(BootActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}
	};

}
