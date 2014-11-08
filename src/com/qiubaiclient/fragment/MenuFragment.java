package com.qiubaiclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiubaiclient.customui.CircleImageView;
import com.qiubaiclient.customui.PullScrollView;
import com.qiubaiclient.main.AboutAcivity;
import com.qiubaiclient.main.R;
import com.qiubaiclient.utils.Common;
import com.qiubaiclient.utils.FastBlur;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

/**
 * 菜单Fragment
 * 
 * @author xiangxm
 * 
 */
public class MenuFragment extends BaseFragment implements
		PullScrollView.OnTurnListener {

	// 操作分享对象
	UMSocialService mController = null;

	private static final String USER_NAME = "USER_NAME";

	public MenuFragment() {
		super();
	}

	private Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 获取xml视图。
		return inflater.inflate(R.layout.menuleft_fragment, container, false);
	}

	private com.qiubaiclient.customui.PullScrollView pullScrollView;
	private ImageView headImgView;

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		initView(view);

	}

	private TextView menuTxtUserName;
	private CircleImageView circleImageView;
	public DisplayImageOptions loginOptions;
	/**
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;

	/**
	 * 初始化界面视图
	 */
	private void initView(View view) {

		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.blur_background);

		// TODO Auto-generated method stub
		pullScrollView = (PullScrollView) view.findViewById(R.id.scroll_view);
		headImgView = (ImageView) view.findViewById(R.id.background_img);
		//设置高斯模糊
		headImgView.setImageBitmap(Common.blurBitmap(bitmap, mContext));
		
		pullScrollView.setHeader(headImgView);
		pullScrollView.setOnTurnListener(this);

		// 显示用户名
		menuTxtUserName = (TextView) view.findViewById(R.id.menu_txt_username);
		String name = Common.preferenceUtils.readConfig(USER_NAME, "");
		if (!name.equals("")) {

			menuTxtUserName.setText(name);
		}
		// 显示用户头像
		circleImageView = (CircleImageView) view
				.findViewById(R.id.menu_img_userimg);
		loginOptions = new DisplayImageOptions.Builder()

		.showStubImage(R.drawable.default_users_avatar)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage("", circleImageView, loginOptions);
		// 添加各个栏目事件
		int ids[] = new int[] { R.id.shareView, R.id.aboutView,
				R.id.settingView };
		for (int id : ids) {

			view.findViewById(id).setOnClickListener(clickListener);
		}

		// 初始化分享组建
		// initShareTools();
	}

	// 要分享的图片
	private UMImage mUMImgBitmap = null;

	/**
	 * 初始化分享组建
	 */
	private void initShareTools() {
		// TODO Auto-generated method stub

		mController = UMServiceFactory.getUMSocialService("com.umeng.share");

		// 设置分享的内容
		String mShareContent = "test";
		// 设置分享内容
		mController.setShareContent(mShareContent);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blur_background);

		// 设置分享的图片
		mUMImgBitmap = new UMImage(mContext, bitmap);
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
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
				"1103445196", "U0I3OCuMn5yeOwPm");
		qqSsoHandler.addToSocialSDK();

		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(),
				"1103445196", "U0I3OCuMn5yeOwPm");
		qZoneSsoHandler.addToSocialSDK();

		// 添加人人网SSO授权功能
		// APPID:273033
		// API Key:8b7710d0cd0948c39aed43fd0d21ccb1
		// Secret:1fa74f7f09cc4308a73c3bc267ba6f8d
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(getActivity(),
				"273033", "8b7710d0cd0948c39aed43fd0d21ccb1",
				"1fa74f7f09cc4308a73c3bc267ba6f8d");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

		// 添加短信
		// SmsHandler smsHandler = new SmsHandler();
		// smsHandler.addToSocialSDK();

		// 添加email
		// EmailHandler emailHandler = new EmailHandler();
		// emailHandler.addToSocialSDK();
		//
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

	}

	/**
	 * 设置毛玻璃
	 * 
	 * @param bkg
	 * @param view
	 */
	private void blur(Bitmap bkg, View view) {
		// TODO Auto-generated method stub
		long startMs = System.currentTimeMillis();
		// float scaleFactor = 8;
		float radius = 2;

		Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
		// (int) (view.getMeasuredWidth() / scaleFactor),
		// (int) (view.getMeasuredHeight() / scaleFactor),
				(int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.scale(1, 1);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);

		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
		System.out.println(System.currentTimeMillis() - startMs + "ms");
	}

	/**
	 * 事件监听
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			if (null == view) {

				return;
			}
			Intent intent = null;
			switch (view.getId()) {

			case R.id.shareView:

				// false代表是否需要强制登录
				// mController.openShare(mActivity, false);
				// 分享
				Common.shareTo((Activity) mContext, "糗大了Android客户端，赶紧应用商店下载。");
				break;
			case R.id.aboutView:
				intent = new Intent();
				intent.setClass(mContext, AboutAcivity.class);
				startActivity(intent);
				((Activity) mContext).overridePendingTransition(
						R.anim.slide_right_in, R.anim.slide_left_out);
				break;
			case R.id.settingView:
				break;
			}

		}
	};

	@Override
	public void onTurn() {
		// TODO Auto-generated method stub
		// 这里可以处理一些刷新现实内容方面的东西

	}

}
