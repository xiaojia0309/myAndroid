package com.example.john.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;


import com.example.john.myapplication.R;

import java.io.File;


/**
 * 开屏页
 *
 */
public class SplashActivity extends Activity {


//	private TextView versionText;

	private static final int sleepTime = 2000;

	@Override
	protected void onCreate(Bundle arg0) {

		final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		super.onCreate(arg0);

		// 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，显示在屏幕不会有错位（去掉这一行试试就知道效果了）
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initFile() ;

//		versionText = (TextView) findViewById(R.id.tv_version);

//		versionText.setText(getVersion());
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		view.startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();

		new Thread(new Runnable() {
			public void run() {
				if (true) {
					// ** 免登陆情况 加载所有本地群和会话
					//不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
					//加上的话保证进了主页面会话和群组都已经load完毕
					long start = System.currentTimeMillis();
					long costTime = System.currentTimeMillis() - start;
					//等待sleeptime时长
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					//
					//进入主页面
					startActivity(new Intent(SplashActivity.this, Login.class));
					finish();
				}else {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}
			}
		}).start();

	}

	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号错误";
		}
	}
	@SuppressLint("SdCardPath")
	public void initFile() {

		File dir = new File("/sdcard/jiaYongLu");

		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
