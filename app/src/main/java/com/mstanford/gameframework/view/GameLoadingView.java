package com.mstanford.gameframework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;


import com.example.a01.myapplication.R;
import com.mstanford.gameframework.thread.MyGameThread;

public class GameLoadingView extends SurfaceView implements Callback {
	private MyGameThread gameThread;

	private TextView textview;

	private Canvas canvas;

	private Paint paint;

	private Bitmap[] bmpRobot;
	private Bitmap bpRobot,background;
	public GameLoadingView(Context context)
	{
		super(context);
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
		initialize();

		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
		gameThread = new MyGameThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {
				textview.setText(m.getData().getString("text"));
			}
		},background,bmpRobot);
		
		

		

		
		setFocusable(true);
	}
	
	

	public GameLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
	initialize();

		gameThread = new MyGameThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {
				textview.setText(m.getData().getString("text"));
			}
		},bmpRobot);
		
		

		

		
		setFocusable(true);

	}

	private void initialize() {
		// TODO 自动生成的方法存根
		bmpRobot=new Bitmap[20];
		//bpRobot=BitmapFactory.decodeResource(this.getResources(),R.drawable.robot);
		bmpRobot [1]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load1);
		bmpRobot [2]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load2);
		bmpRobot [3]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load3);
		bmpRobot [4]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load4);
		bmpRobot [5]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load5);
		bmpRobot [6]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load6);
		bmpRobot [7]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load7);
		bmpRobot [8]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load8);
		bmpRobot [9]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load9);
		bmpRobot [10]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load10);
		bmpRobot [11]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load11);
		bmpRobot [12]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load12);
		bmpRobot [13]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load13);
		bmpRobot [14]= BitmapFactory.decodeResource(this.getResources(),	R.drawable.load14);
	}



	public MyGameThread getThread() {
		return gameThread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		gameThread.setSurfaceSize(width, height);
		gameThread.setRunning(true);
		if (gameThread.isAlive()) {
			Log.v(this.getClass().getName(), "unpause gameThread");
			gameThread.unpause();
		} else {
			Log.v(this.getClass().getName(), "start gameThread");
			gameThread.start();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		Log.v(this.getClass().getName(), "surfaceCreated()");
		canvas=holder.lockCanvas();
		canvas.drawColor(Color.BLACK);
		
		Paint p = new Paint();
       p.setColor(Color.RED);
       //canvas .drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getWidth()/3, p);
       //canvas.drawBitmap(bmpRobot, 0, 0,null);
       //canvas.save(); 
       holder.unlockCanvasAndPost(canvas); 
		//gameThread.doStart();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		Log.v(this.getClass().getName(), "surfaceDestroyed");
	}

}
