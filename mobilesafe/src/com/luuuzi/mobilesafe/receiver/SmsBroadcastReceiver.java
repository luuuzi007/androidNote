package com.luuuzi.mobilesafe.receiver;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

/**
 * 接收短信(刚发来的短信而不是短信箱中的短信)后播放报警音乐
 * @author admin
 *
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//1.判断是否开启防盗保护
		boolean open_security_str = spUtil.getBooean(context, ConstantUtil.OPEN_SECURITY, false);
		//2.开启防盗保护后，获取短信内容
		if(open_security_str){
			//2.1拿到接收短信数组对象(pdus是短信的key)
			Object[] object = (Object[]) intent.getExtras().get("pdus");
			//2.2遍历短信数组
			for (Object smsObject : object) {
				//2.3获取短信的单个对象
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsObject);
				//2.4拿到发送短信过来的电话号码
				String address = sms.getOriginatingAddress();
				//2.5拿到短信的具体内容
				String smsBody = sms.getDisplayMessageBody();
				//3.判断内容中是否包含播放音乐的关键字
				if(smsBody.contains("#*alarm*#")){
					//4.播放音乐
					//参数1(context):上下文
					//参数2(resid):资源文件路径(音乐文件固定存储带res/raw文件下,raw需要自己创建)
					MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
					//设置无限循环播放
					mediaPlayer.setLooping(true);
					//启动
					mediaPlayer.start();
				}
			}
		}
	}

}
