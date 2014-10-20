package com.qiubaiclient.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiubaiclient.main.R;

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
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;
	/**
	 * 图片下载器配置
	 */
	public static DisplayImageOptions options;


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
		//初始化图片缓存工具
		options = new DisplayImageOptions.Builder()
				 .showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
	}

}
