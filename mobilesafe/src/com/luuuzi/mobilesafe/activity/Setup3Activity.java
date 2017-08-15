package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.ToastUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Setup3Activity extends Activity{
	private Context mContext;
	private String tag="Setup3Activity";
	private EditText et_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_setup3);
		initUI();
	}
	/**
	 * 初始化UI
	 */
	private void initUI() {
		et_phone = (EditText) findViewById(R.id.et_phone);
		//回显联系人电话号码
		et_phone.setText(spUtil.getString(mContext, ConstantUtil.CONTACT_PHONE, ""));
		
	}
	/**
	 * 上一页点击按钮previousPage
	 * @param view
	 */
	public void previousPage3(View view){
		Intent intent = new Intent(mContext,Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
	/**
	 * 下一页点击事件
	 * @param view
	 */
	public void nextPage(View view){
		String phone = et_phone.getText().toString().trim().replace(" ", "");
		//只有绑定了电话号码才让其跳转至下一页
		if (!TextUtils.isEmpty(phone)) {
			//点击下一页按钮时保存到私有目录
			spUtil.putString(mContext, ConstantUtil.CONTACT_PHONE,
					phone);
			Intent intent = new Intent(mContext,Setup4Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		}else{
			ToastUtil.show(mContext, "请绑定手机号");
		}
	}
	
	/**	选择联系人按钮的点击事件
	 * @param view
	 */
	public void contact(View view){
		Intent intent = new Intent(mContext,ContactActivity.class);
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//拿到ContactActivity界面传过来的结果码判断
		if(resultCode==1){
			String phone_str = data.getStringExtra("phone");
			//Log.i(tag, "返回的电话号码为："+phone_str);
			//设置给edittext
			et_phone.setText(phone_str);
			
		}
	}
}
