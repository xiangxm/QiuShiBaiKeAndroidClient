package com.qiubaiclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <p>
 * 配置文件工具类
 * </p>
 * 
 */
public class SharePreferenceUtils {

	private SharePreferenceUtils() {
	}

	public static SharePreferenceUtils instance = new SharePreferenceUtils();

	/**
	 * 单例模式 获取SharePreferenceUtils 对象
	 * 
	 * @param mContext
	 * @return SharePreferenceUtils
	 */
	public static SharePreferenceUtils getInstance(Context mContext) {
		if (null == share) {

			share = mContext.getSharedPreferences("perference",
					Context.MODE_PRIVATE);
			editor = share.edit();
		}
		return instance;
	}

	private static SharedPreferences share;
	private static Editor editor;

	/**
	 * 保存配置信息
	 * 
	 * @param mContext
	 * @param key
	 * @param value
	 */
	public void writeConfig(String key, String value) {

		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 保存配置信息boolean值
	 * 
	 * @param key
	 * @param value
	 */
	public void writeBolConfig(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 保存整数配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void writeIntConfig(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 读取配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String readConfig(String key, String defValue) {

		String str = share.getString(key, defValue);

		return str;

	}

	/**
	 * 读取boolean配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean readBolConfig(String key, boolean defValue) {

		boolean bol = share.getBoolean(key, defValue);

		return bol;

	}

	/**
	 * 读取整数配置保存信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int readIntConfig(String key, int defValue) {

		int result = share.getInt(key, defValue);

		return result;

	}

}
