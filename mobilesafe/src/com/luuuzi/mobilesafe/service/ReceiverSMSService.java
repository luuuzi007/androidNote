package com.luuuzi.mobilesafe.service;

import javax.naming.directory.ModificationItem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

/**
 * 1.接收短信的时候，会发送广播，对系统的广播进行监听
 * 2.监听广播内容，如果内容中包含关键字#*location*#,开启服务(不要和Activity绑定的方法启动服务) 3.在服务中去获取经纬度
 * 
 * @author admin
 * 
 */
public class ReceiverSMSService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 获取位置管理者对象
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 以最优的方式去获取经纬度的坐标(根据实际情况去选择3种情况(网络定位，基站定位，gps定位)的一种方式去拿坐标)
		Criteria criteria = new Criteria();
		// 允许使用流量(花费)
		criteria.setCostAllowed(true);
		// 设置定位精确度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 获取最优的获取经纬度的方式。参数2(enabledOnly):是否允许,只能填true
		lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				new LocationListener() {
					// gps信号发生改变时监听
					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					// 位置发生改变的事件监听
					@Override
					public void onLocationChanged(Location location) {
						// 经度
						double longitude = location.getLongitude();
						// 纬度
						double latitude = location.getLatitude();
						// 将坐标已短信的形式发送给紧急联系人
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage("5556", null, "longitude:"
								+ longitude + ",latitude:" + latitude, null,null);

					}
				});

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
