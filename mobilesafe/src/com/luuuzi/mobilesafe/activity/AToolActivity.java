package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AToolActivity extends Activity implements OnClickListener {
	private TextView tv_query_address;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool);
		mContext=this;
		tv_query_address = (TextView) findViewById(R.id.tv_query_address);
		tv_query_address.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_query_address:
			//跳转至归属地查询
			startActivity(new Intent(mContext, QureyAddressActivity.class));
			break;
		}
		
	}
}
