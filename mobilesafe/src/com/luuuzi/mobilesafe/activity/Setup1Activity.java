package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.ToastUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Setup1Activity extends Activity{
	private Context mContext;
	private String tag="Setup1Activity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		Log.i(tag, "setup1界面");
		//通过sharedPreferences存储一个Boolean的值来判断手机防盗是否设置完成
		boolean setup_over = spUtil.getBooean(mContext, ConstantUtil.SETUPOVER, false);
		if(setup_over){
			//已经设置直接跳转至设置完成界面
			setContentView(R.layout.activity_setupover_over);
			initUI();
		
			
		}else{
			//未设置跳转至设置界面1
			setContentView(R.layout.activity_setup1);
		}
	}
	/**
	 * 设置完成界面初始化UI
	 */
	private void initUI() {
		//显示安全号码
		TextView tv_security_number = (TextView) findViewById(R.id.tv_security_number);
		String security_number = spUtil.getString(mContext, ConstantUtil.CONTACT_PHONE, "");
		tv_security_number.setText(security_number);
		//重新进入设置向导
		TextView tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
		tv_reset_setup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击后就重新设置安全标识为false
				spUtil.pullBooean(mContext, ConstantUtil.SETUPOVER, false);
				//将绑定sim卡序列号设置为kong 
				spUtil.putString(mContext, ConstantUtil.SIM_SIMSERIALNUMBER, null);
				//将紧急联系人清空
				spUtil.putString(mContext, ConstantUtil.CONTACT_PHONE, null);
				//将是否开启安全防盗清空
				spUtil.pullBooean(mContext, ConstantUtil.OPEN_SECURITY, false);
				
				//跳转至设置界面1
				Intent intent = new Intent(mContext, Setup1Activity.class);
				startActivity(intent);
				
			}
		});
		
	}
	/**下一页点击事件
	 * @param v
	 */
	public void nextPage1(View v){
		//跳转至设置界面2
		Intent intent = new Intent(mContext, Setup2Activity.class);
		startActivity(intent);
		//关闭当前Activity
		finish();
		//界面跳转设置动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
	
}
