package com.lidroid.xutils.custom.snow;

import java.util.Timer;
import java.util.TimerTask;

import com.lidroid.xutils.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	private FlowerView mFlowerView;
	// 屏幕宽度
	public static int screenWidth;
	// 屏幕高度
	public static int screenHeight;
	Timer myTimer = null;
	TimerTask mTask = null;
	private static final int SNOW_BLOCK = 1;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			mFlowerView.inva();
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//mFlowerView = (FlowerView) findViewById(R.id.flowerview);
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();

		DisplayMetrics dis = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dis);
		float de = dis.density;
		mFlowerView.setWH(screenWidth, screenHeight, de);
		mFlowerView.loadFlower();
		mFlowerView.addRect();

		myTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = SNOW_BLOCK;
				mHandler.sendMessage(msg);
			}
		};
		myTimer.schedule(mTask, 3000, 10);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mFlowerView.recly();
	}
}