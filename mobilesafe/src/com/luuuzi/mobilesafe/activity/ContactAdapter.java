package com.luuuzi.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.luuuzi.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<HashMap<String, String>> contactList;
	public ContactAdapter(Context context,ArrayList<HashMap<String, String>> contactList){
		mContext=context;
		this.contactList=contactList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactList.size();
	}

	@Override
	public HashMap<String, String> getItem(int arg0) {
		// TODO Auto-generated method stub
		return contactList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder=null;
		if(arg1==null){
			arg1=View.inflate(mContext, R.layout.adapter_contact, null);
			holder=new ViewHolder();
			holder.tv_contact=(TextView) arg1.findViewById(R.id.tv_contact);
			holder.tv_phonenumber=(TextView) arg1.findViewById(R.id.tv_phonenumber);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		//添加数据
		holder.tv_contact.setText(contactList.get(arg0).get("name"));
		holder.tv_phonenumber.setText(contactList.get(arg0).get("phone"));
		return arg1;
	}
	class ViewHolder{
		TextView tv_contact;
		TextView tv_phonenumber;
	}
}
