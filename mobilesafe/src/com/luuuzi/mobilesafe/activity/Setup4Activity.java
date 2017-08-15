package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.ToastUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 设置页面4
 * @author admin
 *
 */
public class Setup4Activity extends Activity{
	private Context mContext;
	private CheckBox cb_openSecurity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_setup4);
		initUI();
	}
	/**
	 * 初始化ui
	 */
	private void initUI() {
		cb_openSecurity = (CheckBox) findViewById(R.id.cb_open_security);
		//1.获取之前的状态
		boolean openSecurity = spUtil.getBooean(mContext, ConstantUtil.OPEN_SECURITY, false);
		//设置CheckBox的显示效果
		if(openSecurity){
			cb_openSecurity.setText("手机防盗已开启");
		}else{
			cb_openSecurity.setText("开启手机防盗保护");
		}
		//2.设置checkbox的选中状态
		cb_openSecurity.setChecked(openSecurity);
		//3.checkbox点击事件的设置
		cb_openSecurity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//isChecked是点击后CheckBox的选中状态
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//存储到sp中
				spUtil.pullBooean(mContext, ConstantUtil.OPEN_SECURITY, isChecked);
				if (isChecked) {
					cb_openSecurity.setText("手机防盗已开启");
				}else{
					cb_openSecurity.setText("开启手机防盗保护");
				}
			}
		});
	}
	/**
	 * 上一页按钮点击事件
	 * @param view
	 */
	public void previousPage4(View view){
		Intent intent = new Intent(mContext, Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
	
	/**
	 * 下一页点击事件nextPage4
	 * @param view
	 */
	public void nextPage4(View view){
		if(cb_openSecurity.isChecked()){
			//选中才让其跳转至下一页
			Intent intent = new Intent(mContext,Setup1Activity.class);
			startActivity(intent);
			finish();
			//设置完成将手机防盗的值改变为true
			spUtil.pullBooean(mContext, ConstantUtil.OPEN_SECURITY, cb_openSecurity.isChecked());
			//保存设置完成标识
			spUtil.pullBooean(mContext, ConstantUtil.SETUPOVER, true);
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		}else{
			ToastUtil.show(mContext, "请开始手机防盗");
		}
	
	}
}
