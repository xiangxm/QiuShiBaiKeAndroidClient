package com.qiubaiclient.app;

import android.app.Application;
import android.content.Context;

import com.qiubaiclient.utils.Common;
import com.qiubaiclient.utils.SharePreferenceUtils;

/**
 * Application全局变量类，存在与整个app运行周期。
 * 
 * @author xiangxm
 * 
 */
public class MyApplication extends Application {

	private MyApplication instance = null;

	private Context mContext;

	/**
	 * 单例模式获取Application 对象昂
	 * 
	 * @return
	 */
	public MyApplication getInstance() {

		if (null == instance) {

			synchronized (instance) {

				if (null == instance) {

					instance = new MyApplication();
				}
			}
		}

		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = this;
		Common.preferenceUtils = SharePreferenceUtils.getInstance(this);
		// 记录日志
		// 初始化处理全局崩溃处理的类。
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);

	}

}
