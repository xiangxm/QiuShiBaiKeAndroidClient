package com.qiubaiclient.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.qiubaiclient.main.R;
import com.qiubaiclient.model.UserBean;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

/**
 * 提供公共方法的工具类
 * 
 * 
 */
public class Common {

	/**
	 * 存储一些信息
	 */
	public static SharePreferenceUtils preferenceUtils;
	/**
	 * 
	 */
	public static UserBean userBean;
	/**
	 * 手机唯一识别码
	 */
	public static String MAC_ADDRESS = "";

	/**
	 * 获取屏幕的宽高
	 * 
	 * @param con
	 * @return
	 */
	public static int[] getScreenParams(Context con) {

		WindowManager manager = ((Activity) con).getWindowManager();
		return new int[] { manager.getDefaultDisplay().getWidth(),
				manager.getDefaultDisplay().getHeight() };
	}

	/**
	 * 重置图片大小
	 * 
	 * 
	 * @param c
	 * @param path
	 *            路径
	 */
	public static Bitmap resizeBitmap(Context c, String path) {

		// 现获取屏幕的宽度
		int windowWidth = ((Activity) c).getWindowManager().getDefaultDisplay()
				.getWidth();
		int windowHeight = ((Activity) c).getWindowManager()
				.getDefaultDisplay().getHeight();

		// 图片解析的配置
		BitmapFactory.Options options = new Options();
		// 不去真的解析图片，只是获取图片的头部信息宽，高
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 获取图片的宽高值
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		// 计算缩放比例
		int scaleX = imageWidth / windowWidth;
		int scaleY = imageHeight / windowHeight;
		int scale = 1;
		if (scaleX > scaleY & scaleY >= 1) {
			scale = scaleX;

		} else if (scaleY > scaleX & scaleX >= 1) {
			scale = scaleY;

		}
		// 真的解析图片
		options.inJustDecodeBounds = false;
		// 设置采样率
		options.inSampleSize = scale;
		// 重新获取Bitmap对象
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		return bitmap;
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return ddddddd
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 将OBJ转换成字符串
	 * 
	 * @param obj
	 *            被转化对象
	 * @return obj的字符串表示形式
	 */
	public static String object2String(Object obj) {

		String result = "";
		if (obj != null) {

			result = String.valueOf(obj);
		}

		return result;
	}

	/**
	 * 字符串转换成int
	 * 
	 * @param str
	 *            字符串参数
	 * @return
	 */
	public static int String2int(String str) {

		if (str != null && !str.equals("")) {

			return Integer.parseInt(str);
		}

		return -1;
	}

	/**
	 * 解析json数据插入数据库
	 * 
	 * @param context
	 * @param id
	 *            资源文件对应的id
	 */
	public static String readData(Context context, int id) {
		// 将资源文件读取出来
		InputStream in = context.getResources().openRawResource(id);
		// 将in读入reader 中
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(in, "GBK"));

			StringBuffer buffer = new StringBuffer("");
			String tem = "";
			while ((tem = br.readLine()) != null) {
				buffer.append(tem);
			}
			br.close();

			return buffer.toString();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查当前手机网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetConnected(Context context) {
		// 判断连接方式
		boolean wifiConnected = isWifiConnected(context);
		boolean mobileConnected = isMobileConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			// 如果都没有连接返回false，提示用户当前没有网络
			return false;
		}
		return true;
	}

	// 判断手机使用是wifi还是mobile
	/**
	 * 判断手机是否采用wifi连接
	 */
	public static boolean isWifiConnected(Context context) {
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 手机移动网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null)
			date = new Date();
		String code = new String();
		SimpleDateFormat matter = new SimpleDateFormat("MM月dd日 HH:mm");
		code = matter.format(date);
		return code;
	}

	/**
	 * 测试是否是Email地址
	 * 
	 * @param email地址
	 * @return 测试结果
	 */
	public static boolean testEmail(String email) {

		String regex = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";

		return email.matches(regex);
	}

	/**
	 * 进入画面时，关闭键盘
	 * 
	 * @param context
	 */
	public static void closeWhenOncreate(Context context) {

		// SOFT_INPUT_STATE_ALWAYS_VISIBLE 键盘始终显示
		((Activity) context).getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	/**
	 * 
	 * @param context
	 *            view所在activity
	 * @param view
	 *            当前activity中获取焦点的view
	 */
	public static void closeKeyboardForCommonAct(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) ((Activity) context)
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (((Activity) context).getCurrentFocus().getWindowToken() != null) {
			imm.hideSoftInputFromInputMethod(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 普通关闭
	 * 
	 * @param context
	 */
	public static void closeKeyboardCommAct(Context context) {

		InputMethodManager imm = (InputMethodManager) ((Activity) context)
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (((Activity) context).getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	/**
	 * 全角转换成半角，适应屏幕:TextView换行时，全角和半角导致显示混乱。 /全角空格为12288，半角空格为32 /其他字符半角(33
	 * -126)与全角(65281- 65374)的对应关系是：均相差65248
	 * 
	 * @param input
	 * @return
	 */
	public static String changeQuanj2Banj(String input) {

		char[] c = input.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
			}
			if (c[i] > 65280 && c[i] < 65375) {

				c[i] = (char) (c[i] - 65248);
			}
		}
		return String.valueOf(c);

	}

	/**
	 * 检查sd卡是否可用
	 * 
	 * @return
	 */
	public static boolean checkSDCardAvaliable() {

		if (Environment.getExternalStorageState() == Environment.MEDIA_REMOVED) {

			return false;
		}

		return true;
	}

	/**
	 * 检查是否为数字，包括含小数点的
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("(([+\\-])?(\\d+)?)?(\\.(\\d+)?)?$");// (([+\\-])?(\\d+)?)?(\\.(\\d+)?)?$
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查指定服务是否在运行
	 * 
	 * @param context
	 * @param serviceName
	 * @return
	 */
	public static boolean IsServiceRunning(Context context, String serviceName) {

		ActivityManager activityManage = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList = activityManage
				.getRunningServices(30);

		for (ActivityManager.RunningServiceInfo info : serviceList) {

			if (info.service.getClassName().equals(serviceName)) {

				return true;
			} else {
				continue;
			}
		}

		return false;
	}

	/**
	 * 按照特定尺寸计算图片缩放值。倍数
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 原始图片的宽高
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * 根据resid获取图片
	 * 
	 * @param res
	 * @param resId对应图片的资源id
	 * @param reqWidth
	 *            需要的图片宽度
	 * @param reqHeight需要的图片高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// 首先设置 inJustDecodeBounds=true 来获取图片尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// 计算 inSampleSize 的值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// 根据计算出的 inSampleSize 来解码图片生成Bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 获取年月日时间 根据传入的格式
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String getDateFromLong(String time, String pattern) {

		long timeMils = Long.parseLong(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date(timeMils * 1000));

	}

	/**
	 * 将drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		if (w < 0 || h < 0) {

			return null;
		}
		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获取手机屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取手机屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 分享
	 * 
	 * @param ctx
	 */
	public static void shareTo(Activity ctx, String str) {
		if (str == null || str.equals("")) {

			return;
		}
		Intent intent;
		// 分享的intent
		intent = new Intent(Intent.ACTION_SEND);
		// 分享的数据类型
		intent.setType("text/plain");
		// 分享的主题
		intent.putExtra(Intent.EXTRA_SUBJECT, "快捷分享");
		// 分享的内容
		intent.putExtra(Intent.EXTRA_TEXT, str);
		// 允许启动新的Activity
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 目标应用寻找对话框的标题
		ctx.startActivity(Intent.createChooser(intent, "分享给好友"));
	}

	/**
	 * 初始化分享组建
	 */
	public static void initShareTools(Context ctx) {
		// TODO Auto-generated method stub

		UMSocialService mController = UMServiceFactory
				.getUMSocialService("com.umeng.share");

		// 设置分享的内容
		String mShareContent = "test";
		// 设置分享内容
		mController.setShareContent(mShareContent);
		Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(),
				R.drawable.blur_background);
		UMImage mUMImgBitmap;
		// 设置分享的图片
		mUMImgBitmap = new UMImage(ctx, bitmap);
		mController.setShareImage(mUMImgBitmap);
		mController.setAppWebSite("http://www.qiushibaike.com"); // 设置应用地址

		// 添加新浪和qq空间的SSO授权支持
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加腾讯微博SSO支持
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		// String appID = "";
		// // 添加微信平台
		// UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID);
		// wxHandler.addToSocialSDK();
		// // 支持微信朋友圈
		// UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID);
		// wxCircleHandler.setToCircle(true);
		// wxCircleHandler.addToSocialSDK();
		//
		// // 设置微信好友分享内容
		// WeiXinShareContent weixinContent = new WeiXinShareContent();
		// // 设置分享文字
		// weixinContent.setShareContent(mShareContent);
		// // 设置title
		// weixinContent.setTitle("糗大了Android客户端");
		// // 设置分享内容跳转URL
		// weixinContent.setTargetUrl("");
		// // 设置分享图片
		// weixinContent.setShareImage(mUMImgBitmap);
		// mController.setShareMedia(weixinContent);
		//
		// // 设置微信朋友圈分享内容
		// CircleShareContent circleMedia = new CircleShareContent();
		// circleMedia.setShareContent(mShareContent);
		// // 设置朋友圈title
		// circleMedia.setTitle("糗大了Android客户端");
		// circleMedia.setShareImage(mUMImgBitmap);
		// circleMedia.setTargetUrl("");
		// mController.setShareMedia(circleMedia);

		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) ctx,
				"1103445196", "U0I3OCuMn5yeOwPm");
		qqSsoHandler.addToSocialSDK();

		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) ctx,
				"1103445196", "U0I3OCuMn5yeOwPm");
		qZoneSsoHandler.addToSocialSDK();

		// 添加人人网SSO授权功能
		// APPID:273033
		// API Key:8b7710d0cd0948c39aed43fd0d21ccb1
		// Secret:1fa74f7f09cc4308a73c3bc267ba6f8d
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(
				(Activity) ctx, "273033", "8b7710d0cd0948c39aed43fd0d21ccb1",
				"1fa74f7f09cc4308a73c3bc267ba6f8d");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();

		// QQ分享
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(mShareContent);
		qqShareContent.setTitle("糗大了Android客户端");
		qqShareContent.setShareImage(mUMImgBitmap);
		// qqShareContent.setTargetUrl("http://www.qiushibaike.com");
		mController.setShareMedia(qqShareContent);
		//
		// QQ空间分享
		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(mShareContent);
		// 设置点击消息的跳转URL
		// qzone.setTargetUrl("http://www.qiushibaike.com");
		// 设置分享内容的标题
		qzone.setTitle("糗大了Android客户端");
		// 设置分享图片
		qzone.setShareImage(mUMImgBitmap);
		mController.setShareMedia(qzone);

		mController.openShare((Activity) ctx, false);
	}

	/**
	 * 处理高斯模糊
	 * @param bitmap
	 * @param ctx
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap blurBitmap(Bitmap bitmap,Context ctx) {

		// Let's create an empty bitmap with the same size of the bitmap we want
		// to blur
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		// Instantiate a new Renderscript
		RenderScript rs = RenderScript.create(ctx);

		// Create an Intrinsic Blur Script using the Renderscript
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs,
				Element.U8_4(rs));

		// Create the Allocations (in/out) with the Renderscript and the in/out
		// bitmaps
		Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
		Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

		// Set the radius of the blur
		blurScript.setRadius(25.f);

		// Perform the Renderscript
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);

		// Copy the final bitmap created by the out Allocation to the outBitmap
		allOut.copyTo(outBitmap);

		// recycle the original bitmap
		bitmap.recycle();

		// After finishing everything, we destroy the Renderscript.
		rs.destroy();

		return outBitmap;

	}
}
