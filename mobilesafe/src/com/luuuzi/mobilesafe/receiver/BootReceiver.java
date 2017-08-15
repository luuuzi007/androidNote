package com.luuuzi.mobilesafe.receiver;

import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * sim卡变更broadcastreceiver
 * @author admin
 *
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 1.拿到config文件中的原先sim卡的序列号
	/*	String old_simserialnumber = spUtil.getString(context, ConstantUtil.SIM_SIMSERIALNUMBER, "");
		//2.拿到当前更换sim卡后的序列号
		TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String new_simserialnumber = telephonyService.getSimSerialNumber();
		//3.比较2个序列号
		if(!old_simserialnumber.equals(new_simserialnumber)){*/
			//4.如果不相同，则发送短信给紧急联系人
			SmsManager smsManager = SmsManager.getDefault();
			/**
			 * -参数1(destinationAddress)：目标电话号码 
-- 				参数2(scAddress)：短信中心号码，测试可以不填 
-- 				参数3(text): 短信内容 
-- 				参数4(sentIntent)：发送 -->中国移动 --> 中国移动发送失败 --> 返回发送成功或失败信号 --> 后续处理   即，这个意图包装了短信发送状态的信息 
--				 参数5(deliveryIntent)： 发送 -->中国移动 --> 中国移动发送成功 --> 返回对方是否收到这个信息 --> 后续处理  即：这个意图包装了短信是否被对方收到的状态信息（供应商已经发送成功，但是对方没有收到）。
			 */
			smsManager.sendTextMessage("5556", null, "sim卡已变更", null, null);
			Log.i("BootReceiver", "发送短信");
			
		//}
		
	}
}
