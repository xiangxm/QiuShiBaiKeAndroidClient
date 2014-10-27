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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;
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
	/**
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;

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
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		((ImageView) findViewById(R.id.btn_downloadimage))
				.setOnClickListener(this);

		// imageLoader.displayImage(imageUrl, gestureImageView, options);
		int screen[] = Common.getScreenParams(this);
		imageLoader.loadImage(imageUrl, new ImageSize(screen[0], screen[1]),
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub

						if (null != loadedImage) {

							gestureImageView.setImageBitmap(loadedImage);
						}
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub

					}
				});
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
