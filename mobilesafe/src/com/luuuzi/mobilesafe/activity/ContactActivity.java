package com.luuuzi.mobilesafe.activity;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.luuuzi.mobilesafe.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactActivity extends Activity{
	private String tag;
	private Context mContext;
	private ListView lv_contact;
	/**
	 * 存储联系人
	 */
	private ArrayList<HashMap<String,String>> contactList=new ArrayList<HashMap<String, String>>();
	
	/**
	 * 消息机制
	 */
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			ContactAdapter adapter = new ContactAdapter(mContext, contactList);
			lv_contact.setAdapter(adapter);
		};
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		mContext=this;
		tag="contactActivity";
		initUI();
		initData();
	}

	/**
	 * 获取联系人数据的方法,属于耗时操作要在子线程执行
	 */
	private void initData() {
		new Thread(){
			@Override
			public void run() {
				//查询raw_contacts表，拿到uri：contacts://com.android.contacts/表名
				String uriString = new String("content://com.android.contacts/raw_contacts");
				Uri uri = Uri.parse(uriString);
				contactList.clear();
				//1.获得内容解析者对象
				ContentResolver contentResolver = getContentResolver();
				/**
				 * 删除姓名为空的联系人
				int i = contentResolver.delete(uri,"_id=?", new String[]{"1"});
				Log.i(tag, "删除记录条数：========"+i);
				 */
				Cursor cursor = contentResolver.query(uri, new String[]{"contact_id"}, null, null, null);
				//2.循环游标，直到没有数据为止,先判断是否为空在循环
				if(cursor==null){
					Log.i(tag, "没有查询到联系人");
				}else{
					//3.循环联系人表，拿到联系人的唯一标识 contact_id
					while(cursor.moveToNext()){
						String contact_id = cursor.getString(0);
						Log.i(tag, "联系人id:"+contact_id);
						//4.根据用户唯一性的id(contact_id)查询data表和mimetype表生成的视图，
						//通过唯一标识拿到data1(data表中的字段)和mimetype(mimetype表中的字段)
						Cursor indexCursor = contentResolver.
								query(Uri.parse("content://com.android.contacts/data"),
								new String[]{"data1","mimetype"},
								"raw_contact_id=?", 
								new String[]{contact_id}, null);
						//创建一个集合存储联系人
						HashMap<String,String> hashMap = new HashMap<String, String>();
						//循环
						while(indexCursor.moveToNext()){
							
							//data1字段
							String data1_str = indexCursor.getString(0);
							//mimetype字段
							String mimetype_str = indexCursor.getString(1);
							//存储
							Log.i(tag, "data1="+data1_str+";mimetype="+mimetype_str);
							//5.存储,数据不为空时在存储
							if (!TextUtils.isEmpty(data1_str)) {
								if (mimetype_str.equals("vnd.android.cursor.item/phone_v2")) {
									//电话号码
									hashMap.put("phone", data1_str.replace(" ", ""));
								}else if(mimetype_str.equals("vnd.android.cursor.item/name")){
									//姓名
									hashMap.put("name", data1_str);
								}
							}
						}
						//关闭游标
						indexCursor.close();
						//将单个联系人的map集合存储到ArrayList中
						contactList.add(hashMap);
					}
					//Log.i(tag, "联系人集合===="+contactList.toString());
				}
				//3.关闭游标
				cursor.close();
				//发送消息给主线程更新ui
				Message message = Message.obtain();
				//发送一个空的消息(因为不需要消息传数据)，告诉主线程可以去使用子线程已经填充好的数据集合
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	private void initUI() {
		lv_contact = (ListView) findViewById(R.id.lv_contact);	
		//点击事件，点击item后拿到联系人并返回到setup3界面
		lv_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//通过索引拿到电话号码
				//在结束页面的时候将电话号码数据传过去
				String phone_str = contactList.get(position).get("phone");
				Intent intent = new Intent();
				//通过intent携带数据传递
				intent.putExtra("phone", phone_str);
				//传一个结果码，并携带数据,
				//返回的Activity的OnActivityResult()方法中通过结果码判断是哪个Activity传来的
				setResult(1, intent);
				finish();
			}
		});
	}
}
