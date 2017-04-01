package com.mstanford.gameframework.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.example.myproject.BootActivity;

public class MyGameThread extends Thread implements Runnable {

	private static final String TAG = "AudioRecord";
	// 游戏状态值:ready
	public final static int GS_READY = 0;

	// 游戏线程每执行一次需要睡眠的时间
	private final static int DELAY_TIME = 100;

	// 上下文,方便获取到应用的各项资源,如图片、音乐、字符串等
	private Context context;

	// 与Activity其他View交互用的handler
	private Handler handler;

	// 由SurfaceView提供的SurfaceHolder
	private SurfaceHolder surfaceHolder;

	// 游戏线程运行开关
	private boolean running = false;

	// 游戏状态״̬
	private int gameState;

	private double volume;
	private Boolean isLanding;

	// 当前surface/canvas的高度,在surfaceChanged方法中被设置
	private int mCanvasHeight = 1;

	// 当前surface/canvas的宽度,在surfaceChanged方法中被设置

	private int mCanvasWidth = 1;

	private float iRadius, q;

	private double lastVolum;

	/**
	 * 游戏是否暂停
	 */
	private boolean isPaused = false;

	private Bitmap[] bitmap;
	private int count = 1;

	// 机器人位图
	private Bitmap bmpRobot;
	// 机器人的方向常量
	private final int DIR_LEFT = 0;
	private final int DIR_RIGHT = 1;
	// 机器人当前的方向
	private int dir = DIR_RIGHT;
	// 动作帧下标
	private int currentFrame;
	// 机器人的X,Y位置
	private int mScreenWidth, mScreenHeight;

	private VideoThread vt;
	// 处理按键卡现象



	public MyGameThread(SurfaceHolder holder, Context context, Handler handler) {
		this.surfaceHolder = holder;
		this.context = context;
		this.handler = handler;
		// bmpRobot=
		// bmpRobot = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.robot);
		doStart();
	}

	public MyGameThread(SurfaceHolder holder, Context context, Handler handler,
			Bitmap bmpRobot) {
		this.surfaceHolder = holder;
		this.context = context;
		this.handler = handler;
		this.bmpRobot = bmpRobot;
		isLanding = false;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		mScreenWidth = wm.getDefaultDisplay().getWidth();
		mScreenHeight = wm.getDefaultDisplay().getHeight();
		iRadius = 1;
		doStart();
	}

	public MyGameThread(SurfaceHolder holder, Context context, Handler handler,
			Bitmap[] bitmap) {
		this.surfaceHolder = holder;
		this.context = context;
		this.handler = handler;
		this.bitmap = bitmap;
		iRadius = 1;

		doStart();
	}

	public MyGameThread(SurfaceHolder holder, Context context, Handler handler,
			Bitmap background, Bitmap[] bmpRobot) {
		// TODO 自动生成的构造函数存根
		this.surfaceHolder = holder;
		this.context = context;
		this.handler = handler;
		this.bmpRobot = background;

		this.bitmap = bmpRobot;
		isLanding = false;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		mScreenWidth = wm.getDefaultDisplay().getWidth();
		mScreenHeight = wm.getDefaultDisplay().getHeight();
		iRadius = 1;
		vt=new VideoThread(context);
		doStart();

	}

	/**
	 * 设置游戏状态
	 * 
	 * @param mode
	 *            游戏状态״̬
	 */
	public void setState(int mode) {
		synchronized (surfaceHolder) {
			setState(mode, null);
		}
	}

	/**
	 * 设置游戏状态
	 * 
	 * @param mode
	 *            游戏状态
	 * @param message
	 *            设置游戏状态时的附加文字信息
	 */
	public void setState(int mode, CharSequence message) {
		synchronized (surfaceHolder) {
			// TODO
		}
	}

	/**
	 * 暂停游戏
	 */
	public void pause() {
		synchronized (surfaceHolder) {
			isPaused = true;

		}

	}

	/**
	 * 恢复运行游戏
	 */
	public void unpause() {
		// 如果游戏中有时间,别忘记应将其在这里调整到正常
		synchronized (surfaceHolder) {
			isPaused = false;

		}
	}

	/**
	 * 当Activity因销毁而被重新创建时,在这里恢复游戏上次运行的数据
	 * 
	 * @param saveState
	 *            Activity传来的保存游戏数据的容器
	 */
	public void restoreState(Bundle saveState) {
		// TODO
	}

	/**
	 * 在Activity切到后台时保存游戏的数据
	 * 
	 * @param outState
	 *            保存游戏数据的容器
	 */
	public void saveState(Bundle outState) {
		// TODO
	}

	/**
	 * 设置游戏线程运行开关
	 * 
	 * @param b
	 *            开/关
	 */
	public void setRunning(boolean b) {
		running = b;
	}

	/**
	 * ���?�°�����¼�
	 * 
	 * @param keyCode
	 *            �����¼�����ֵ
	 * @param msg
	 *            �����¼�����
	 * @return �Ƿ�����
	 */
	public boolean doKeyDown(int keyCode, KeyEvent msg) {
		synchronized (surfaceHolder) {
			// TODO
			return false;
		}
	}

	/**
	 * 处理弹起按键的事件
	 * 
	 * @param keyCode
	 *            按键事件动作值
	 * @param msg
	 *            按键事件对象
	 * @return 是否处理完
	 */
	public boolean doKeyUp(int keyCode, KeyEvent msg) {

		synchronized (surfaceHolder) {
			// TODO
		}
		return false;
	}

	/**
	 * 设置surface/canvas的宽度和高度
	 * 
	 * @param width
	 *            由SurfaceHolder传来的宽度
	 * @param height
	 *            由SurfaceHolder传来的高度
	 */
	public void setSurfaceSize(int width, int height) {
		// synchronized to make sure these all change atomically
		synchronized (surfaceHolder) {
			mCanvasWidth = width;
			mCanvasHeight = height;

			// /不要忘记每次画布的宽度和高度改变时, 在这里对图片等资源做缩放等相关适配屏幕的处理
			// TODO
		}
	}

	public void run() {
		while (running) {
			if (!isPaused) {
				Canvas c = null;
				try {
					c = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						doDraw(c);
					}

					logic();

				} catch (Exception e) {
					// Log.v("mygamethread", e.getMessage());
				} finally {
					if (c != null) {
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}

				try {
					Thread.sleep(DELAY_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 游戏逻辑处理
	 */
	public void logic() {
		Log.v(this.getClass().getName(), "logic");
		// TODO
		// iRadius+=10*q;
		// q++;
		if (iRadius < mScreenWidth / 1.3) {
			iRadius *= 1.3;

		}
		else{
			isPaused=true;

			vt.execute();






		}
		// iRadius++;
		if (count > 14) {
			count = 1;
		}

	}

	/**
	 * 初始化游戏开始时的参数
	 */
	public void doStart() {
		// TODO

	}

	/**
	 * 游戏绘画
	 */
	private void doDraw(Canvas canvas) {
		Log.v(this.getClass().getName(), "doDraw");
		// canvas.drawColor(Color.WHITE);

		Paint p = new Paint();
		// p.setColor(Color.RED);
		// canvas .drawCircle(canvas.getWidth()/2, canvas.getHeight()/2,
		// canvas.getWidth()/3, p);
		// canvas.drawBitmap(bitmap[count],
		// (canvas.getWidth()-bitmap[count].getWidth())/2,
		// (canvas.getHeight()-bitmap[count].getHeight())/2, null);
		//bmpRobot = Bitmap.createScaledBitmap(bmpRobot, mScreenWidth,
		//		mScreenHeight, true);

		//canvas.drawBitmap(bmpRobot, 0, 0, p);
		canvas.drawColor(Color.WHITE);
		p.setColor(Color.WHITE);
		drawFrame(currentFrame, canvas, p);
		canvas.save();

		// TODO
	}

	/**
	 * 
	 * @param currentFrame
	 *            绘制帧
	 * @param frameW
	 *            每帧的高
	 * @param frameH
	 *            每帧的高
	 * @param canvas
	 *            画布实例
	 * @param paint
	 *            画笔实例
	 */
	private void drawFrame(int currentFrame, Canvas canvas, Paint paint) {
		// 每帧的宽
		// int frameW = bmpRobot.getWidth() / 6;
		// // 每帧的高
		// int frameH = bmpRobot.getHeight() / 2;
		// // 得到位图的列数
		// int col = bmpRobot.getWidth() / frameW;
		// // 得到当前帧相对于位图的X坐标
		// int x = currentFrame % col * frameW;
		// // 得到当前帧相对于位图的Y坐标
		// int y = currentFrame / col * frameH;
		canvas.save();
		// 设置一个宽高与机器人每帧相同大小的可视区域

		// canvas.clipRect(robot_x, robot_y, robot_x + bmpRobot.getWidth() / 6,
		// robot_y + bmpRobot.getHeight() / 2);
		// // canvas.rotate(30);
		// if (dir == DIR_LEFT) {// 如果是向左侧移动
		// // 镜像操作 - 反转 - 改变机器人动画的朝向
		// canvas.scale(-1, 1, robot_x - x + bmpRobot.getWidth() / 2, robot_y
		// - y + bmpRobot.getHeight() / 2);
		// }
		// paint.setStrokeWidth(10);
		// paint.setStyle(Style.STROKE);
		// canvas.clip
		// canvas.drawBitmap(bmpRobot, robot_x - x, robot_y - y, paint);

		canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2,
				iRadius, paint);
		canvas.restore();
		canvas.clipRect((float) (canvas.getWidth() / 2 - iRadius / 1.414),
				(float) (canvas.getHeight() / 2 - iRadius / 1.414),
				(float) (canvas.getWidth() / 2 + iRadius / 1.414),
				(float) (canvas.getHeight() / 2 + iRadius / 1.414));
		canvas.drawBitmap(bitmap[count],
				(canvas.getWidth() - bitmap[count].getWidth()) / 2,
				(canvas.getHeight() - bitmap[count].getHeight()) / 2, null);
		count++;
		canvas.restore();
	}

}
