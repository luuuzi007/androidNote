package com.luuuzi.administration;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	//设备管理器对象
	private DevicePolicyManager mDPM;

	private ComponentName mDeviceAdminSample;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		Button bt_start = (Button) findViewById(R.id.bt_start);
		// 组件对象
		// 参数一：上下文，参数二：自己创建的Receiver对象
		mDeviceAdminSample = new ComponentName(this, DeviceReceiver.class);
		bt_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 激活:跳转到开启设备管理器的Activity
				Intent intent = new Intent(
						DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				// 参数2为组件
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
						mDeviceAdminSample);
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
						"设备管理器");
				startActivity(intent);
			}
		});
		Button bt_lock = (Button) findViewById(R.id.bt_lock);
		//拿到devicePolicyManager(设备管理者)对象，通过该对象去操作手机
		mDPM = (DevicePolicyManager) mContext
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		bt_lock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//完善逻辑：防止没有激活设备管理器就去锁屏，先判断设备管理器是否激活
				if(mDPM.isAdminActive(mDeviceAdminSample)){
					//已经激活，可以锁屏
					mDPM.lockNow();
					//附加功能锁屏同时设置密码
					//参数2(重置密码的时候必须进入了这个应用则填RESET_PASSWORD_REQUIRE_ENTRY，没进入则填0)
					mDPM.resetPassword("123", 0);
				}else{
					Toast.makeText(mContext, "请激活设备管理器", 0).show();
				}
			}
		});
		Button bt_wipe_data = (Button) findViewById(R.id.delete_data);
		bt_wipe_data.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mDPM.isAdminActive(mDeviceAdminSample)){
					//删除手机数据
					mDPM.wipeData(0);
					//删除sdcard数据,不要乱使用
					mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
				}else{
					Toast.makeText(mContext, "请激活设备管理器", 0).show();
				}
			}
		});
		Button bt_unload = (Button) findViewById(R.id.bt_unload);
		bt_unload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//添加系统卸载的Activity的Manifest中的Intent-filter中的action(添加一个就可以)
				Intent intent = new Intent("android.intent.action.VIEW");
				//同样是Manifest中的
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("package:"+getPackageName()));
				startActivity(intent);
			}
		});
	}

}
