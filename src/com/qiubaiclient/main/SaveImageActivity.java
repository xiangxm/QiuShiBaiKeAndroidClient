package com.qiubaiclient.main;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.polites.android.GestureImageView;
import com.qiubaiclient.app.MyApplication;
import com.qiubaiclient.utils.Common;
import com.qiubaiclient.utils.FileUtil;
import com.qiubaiclient.utils.ToastUtil;

/**
 * @author 显示具体消息图片，并且提供下载衔接
 */
public class SaveImageActivity extends Activity implements OnClickListener {
	/**
	 * 图片网络路径
	 */
	private String imageUrl = null;
	/**
	 * 显示图片控件
	 */
	private GestureImageView gestureImageView = null;
	/**
	 * 图片缓存地址
	 */
	private static final String IMAGE_CACHE_PATH = Environment
			.getExternalStorageDirectory() + "/QiuDaLe/IMG_CACHE";

	public static DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.save_image_activity);

		Intent intent = getIntent();
		if (null != intent) {

			this.imageUrl = intent.getStringExtra("articleImgUrl");

			if (null == imageUrl || imageUrl.equals("")) {

				this.imageUrl = "";
			}

		}
		gestureImageView = (GestureImageView) findViewById(R.id.image);
		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		((ImageView) findViewById(R.id.btn_downloadimage))
				.setOnClickListener(this);
		MyApplication.imageLoader.displayImage(imageUrl, gestureImageView,
				options);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btn_downloadimage) {

			if (!Common.isWifiConnected(this)) {

				ToastUtil.show(this, "当前网络不可用!", Toast.LENGTH_SHORT);
				return;
			}
			saveImage();
		}

	}

	/**
	 * 保存图片
	 */
	private void saveImage() {

		String name = FileUtil.getFileName(imageUrl);

		File nameFile = new File(IMAGE_CACHE_PATH, name);
		if (nameFile.exists()) {
			// 下载之前先判断当前文件是否已经存在
			ToastUtil.show(this, "该图片已经下载至本地,请在/QiuDaLe/IMG_CACHE/目录下查看.",
					Toast.LENGTH_SHORT);// 成功下载文件至本地
			return;
		}
		gestureImageView.setDrawingCacheEnabled(true);
		if (FileUtil.writeBitmap2SDcard(imageUrl,
				gestureImageView.getDrawingCache(), IMAGE_CACHE_PATH)) {

			ToastUtil.show(this, "成功下载图片至本地!", Toast.LENGTH_SHORT);
		} else {
			ToastUtil.show(this, "图片下载失败!", Toast.LENGTH_SHORT);
		}
		gestureImageView.setDrawingCacheEnabled(false);

	}
}
