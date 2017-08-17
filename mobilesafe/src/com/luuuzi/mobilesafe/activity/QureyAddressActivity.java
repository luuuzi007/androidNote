package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.engine.AddressDao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QureyAddressActivity extends Activity {
	private EditText et_phone;
	private Button bt_query;
	private TextView tv_query_result;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			tv_query_result.setText((String) msg.obj);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_address);
		initUI();
		queryPhoneNumber();
	}

	/**
	 * 查询电话号码
	 */
	private void queryPhoneNumber() {
		bt_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = et_phone.getText().toString().trim();
				if (!TextUtils.isEmpty(phone)) {
					//不为空查询
					query(phone);
					
				}else{
					//Interpolator:插补器
					//cycleInterpolator :数学的正弦函数，插补器
					
					//为空实现抖动效果
					Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
					//自定义插补器
					shake.setInterpolator(new Interpolator() {
						//y=ax+b; input为x，返回值为y,通过函数确定摆动幅度大小
						@Override
						public float getInterpolation(float input) {
							// TODO Auto-generated method stub
							return 0;
						}
					});
					findViewById(R.id.et_phone_number).startAnimation(shake);
				}
			}
		});

	}

	/**
	 * 查询属于耗时操作，要在子线程中执行
	 * 
	 * @param phone
	 */
	private void query(final String phone) {
		new Thread() {
			@Override
			public void run() {
				String address_str = AddressDao.getAddress(phone);
				// 发送消息给主线程
				Message message = Message.obtain();
				message.obj = address_str;
				mHandler.sendMessage(message);
			}
		}.start();
	}

	private void initUI() {
		et_phone = (EditText) findViewById(R.id.et_phone_number);
		bt_query = (Button) findViewById(R.id.bt_query);
		tv_query_result = (TextView) findViewById(R.id.tv_query_result);
		//实时查询(当EditText输入的内容发送变化就执行)
		et_phone.addTextChangedListener(new TextWatcher() {
			//发生改变
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			//改变之前的事件监听
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			//文本改变之后的事件监听
			@Override
			public void afterTextChanged(Editable s) {
				String phone_str = et_phone.getText().toString().trim();
				query(phone_str);
				
			}
		});
	}
}
