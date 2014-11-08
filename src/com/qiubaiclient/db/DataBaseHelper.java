package com.qiubaiclient.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qiubaiclient.model.ItemBean;

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
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ").append(TABLE_NAME).append(" (")
				.append("_ID ").append(" INTEGER PRIMARY KEY,")
				.append(" image ").append(" TEXT,").append("published_at ")
				.append(" TEXT,").append(" tag ").append("TEXT,")
				.append("user_create_at ").append("TEXT,")
				.append("article_id ").append("TEXT,").append("created_at ")
				.append("TEXT,").append("last_device ").append("TEXT,")
				.append("role ").append("TEXT,").append("last_visited_at ")
				.append("TEXT,").append("state ").append("TEXT,")
				.append("login ").append("TEXT,").append("user_id ")
				.append("TEXT,").append("user_icon ").append("TEXT,")
				.append("image_size_s ").append("INTEGER,")
				.append("image_size_m ").append("INTEGER,").append("down ")
				.append("INTEGER,").append("up ").append("INTEGER,")
				.append("content ").append("TEXT,").append("comments_count ")
				.append("INTEGER,").append("allow_comment ").append("TEXT,")
				.append("blogType ").append(" TEXT ").append(")");
		db.execSQL(sb.toString());

		Log.i(TAG, sb.toString());
		// user表
		//
	}

	/**
	 * 查询数据
	 * 
	 * @param blogType
	 * @return
	 */
	public List<ItemBean> query(int blogType) {
		List<ItemBean> list = new ArrayList<ItemBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from " + TABLE_NAME + " where blogType = '"
				+ blogType + "'";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			do {
				ItemBean item = new ItemBean();
				item.setImage(cursor.getString(cursor.getColumnIndex("image")));
				item.setPublished_at(cursor.getString(cursor
						.getColumnIndex("published_at")));
				item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
				item.setId(cursor.getString(cursor.getColumnIndex("article_id")));
				item.setCreated_at(cursor.getString(cursor
						.getColumnIndex("created_at")));
				if (item.getUser() != null) {
					item.getUser().setCreated_at(
							cursor.getString(cursor
									.getColumnIndex("user_create_at")));
					item.getUser().setLast_device(
							cursor.getString(cursor
									.getColumnIndex("last_device")));
					item.getUser().setRole(
							cursor.getString(cursor.getColumnIndex("role")));
					item.getUser().setLast_visited_at(
							cursor.getString(cursor
									.getColumnIndex("last_visited_at")));
					item.getUser().setState(
							cursor.getString(cursor.getColumnIndex("state")));
					item.getUser().setLogin(
							cursor.getString(cursor.getColumnIndex("login")));
					item.getUser().setId(
							cursor.getString(cursor.getColumnIndex("user_id")));
					item.getUser().setLogin(
							cursor.getString(cursor.getColumnIndex("login")));
					item.getUser()
							.setIcon(
									cursor.getString(cursor
											.getColumnIndex("user_icon")));
				}
				if (item.getVotes() != null) {

					item.getVotes().setDown(
							cursor.getInt(cursor.getColumnIndex("down")));
					item.getVotes().setDown(
							cursor.getInt(cursor.getColumnIndex("down")));
				}
				item.setContent(cursor.getString(cursor
						.getColumnIndex("content")));

				item.setComments_count(cursor.getInt(cursor
						.getColumnIndex("comments_count")));
				// item.setAllow_comment(cursor.getString(cursor
				// .getColumnIndex("allow_comment")));
				item.setBlog_type(cursor.getInt(cursor
						.getColumnIndex("blogType")));
				list.add(item);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}

	// 版本更新时调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 删除表
		Log.e(TAG, "delete DB");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);// 创建新表
	}

	/**
	 * 插入数据
	 * 
	 * @param list
	 */
	public void insert(List<ItemBean> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (ItemBean item : list) {
			ContentValues values = new ContentValues();
			values.put("image", item.getImage());
			values.put("published_at", item.getPublished_at());
			values.put("tag", item.getTag());
			values.put("article_id", item.getId());
			values.put("created_at", item.getCreated_at());
			if (item.getUser() != null) {

				values.put("last_device", item.getUser().getLast_device());
				values.put("role", item.getUser().getRole());
				values.put("last_visited_at", item.getUser()
						.getLast_visited_at());
				values.put("login", item.getUser().getLogin());
				values.put("user_id", item.getUser().getId());
				values.put("user_icon", item.getUser().getIcon());
			}
			values.put("state", item.getState());
			values.put("image_size_m", "");
			values.put("image_size_s", "");
			values.put("down", item.getVotes().getDown());
			values.put("up", item.getVotes().getUp());
			values.put("content", item.getContent());
			values.put("comments_count", item.getComments_count());
			values.put("allow_comment", item.isAllow_comment());
			values.put("blogType", item.getBlog_type());

			db.insert(TABLE_NAME, null, values);
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param blogType
	 *            博客类型
	 */
	public void delete(int blogType) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_NAME + " where blogType = '"
				+ blogType + "'");
	}

}
