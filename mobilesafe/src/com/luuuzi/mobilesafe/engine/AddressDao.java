package com.luuuzi.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddressDao {
	private static String tag="Address";
	static String path = "data/data/com.luuuzi.mobilesafe/files/address.db";
	
	public static String address_str="未知号码";

	// 将电话号码传进去，和数据库中的数据做比较拿到归属地
	public static String getAddress(String phone) {
		address_str="未知号码";
		//正则表达式，匹配号码
		String regularExpression="^1[3-8]\\d{9}";
		//如果phone字符串满足要求则返回true
		if(phone.matches(regularExpression)){
			
			// 拿到用户输入的电话号码前7位
			String phone_str = phone.substring(0, 7);
			// 数据库管理者对象，参数3:(flags):打开方式,读或者写
			SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
			// 通过该对象去查询数据库,参数3：查询条件，可以填表中的任何关键字,去和参数4中的参数做比较，如果相同则取出那一列的数据，否则则不取出
			Cursor cursor = database.query("data1", new String[] { "outkey" },
					"id=?", new String[] { phone_str }, null, null, null);
			while (cursor.moveToNext()) {
				// 拿到outkey的值,和自己要查询的字段数组中对应(参数2)
				String outkey_str = cursor.getString(0);
				//Log.i(tag, outkey_str);
				Cursor cursor2 = database.query("data2", new String[]{"location"}, "id=?", new String[]{outkey_str}, null, null, null);
				while(cursor2.moveToNext()){
					address_str = cursor2.getString(0);
					//Log.i(tag, address_str);
				}
			}
		}
		return address_str;
	}
}
