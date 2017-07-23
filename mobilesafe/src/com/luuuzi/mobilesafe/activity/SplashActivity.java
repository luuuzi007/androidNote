package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.R.layout;
import com.luuuzi.mobilesafe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private Context mContext;
	private TextView tv_version_name;
	private int mVersionCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mContext = this;
		// 初始化ui
		initUi();
		// 获取数据的方法
		initdata();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * 获取数据
	 */
	private void initdata() {
		/**1.应用版本名称*/
		tv_version_name.setText("版本名称："+getVersionName());
		/**2.检测当前版本是否是最新版本(用本地版本号去和服务器端的版本号做比较)，如果不是则提示用户更新(member)*/
		//2.1获得版本号
		mVersionCode = getVersionCode();
		//2.2获取服务器应用的版本号(客户端发请求，服务端做响应(json,xml))
		//http://www.oxx.com/update74.com.json?key=value	返回200则请求成功，流的方式将数据读取出来
	}

	/**	获取当前本地应用(当前应用)版本号
	 * @return 非0 则拿到，0则没有拿到
	 */
	private int getVersionCode() {
		PackageManager packageManager = getPackageManager();
	
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	
		
		
	}

	/**
	 * 获得版本的名称
	 * 
	 * @return 应用的版本名称 返回null表示异常
	 */
	private String getVersionName() {
		// 1.包管理者对象packgeManager
		PackageManager packageManager = getPackageManager();
		try {
			// 2.从包的管理者对象中获得指定包名的具体信息
			// 参数1包名(packageName)：包名，参数2标记(flags):Activity 权限等 0代表获取基本信息
			PackageInfo packageInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			// 3.获得版本名称
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 初始化ui
	 */
	private void initUi() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);

	}
}
