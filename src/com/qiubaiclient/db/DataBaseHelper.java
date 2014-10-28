package com.qiubaiclient.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库辅助操作类
 * 
 * @author xiangxm
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DataBaseHelper";
	/**
	 * 数据库名字
	 */
	private static final String DATABASE_NAME = "qiubig_db";
	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 1;
	/**
	 * 表的名字
	 */
	private static final String TABLE_NAME = "t_qiubig";

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	public DataBaseHelper(Context context) {
		// 创建数据库
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.e(TAG, "create database");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e(TAG, "oncreate DB");
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + "_ID"
				+ " INTEGER PRIMARY KEY," + "title" + " TEXT," + "content"
				+ " TEXT," + "date" + " TEXT," + "img" + " TEXT," + "link"
				+ " TEXT," + "blogType" + " INTEGER" + ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
