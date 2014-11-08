package com.qiubaiclient.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiubaiclient.adapter.CommentsAdapter;
import com.qiubaiclient.customui.CircleImageView;
import com.qiubaiclient.customui.CustomImageButton;
import com.qiubaiclient.customui.DropDownListView;
import com.qiubaiclient.model.ArticleBean;
import com.qiubaiclient.model.ItemBean;
import com.qiubaiclient.model.Page;
import com.qiubaiclient.model.UserBean;
import com.qiubaiclient.utils.AppConfig;
import com.qiubaiclient.utils.Common;
import com.qiubaiclient.utils.ToastUtil;
import com.way.ui.swipeback.SwipeBackActivity;

/**
 * 阅读评论Activity
 * 
 * @author xiangxm
 * 
 */
public class CommentsActivity extends SwipeBackActivity {

	private CircleImageView userImg;
	private TextView tvUserLogin;
	private TextView tvPublishDate;
	private TextView txtContent;
	private ImageView contentImg;
	private CustomImageButton btnOptUp;
	private CustomImageButton btnOptDown;
	private CustomImageButton btnOptShare;
	private CustomImageButton btnOptComments;
	// private PullToRefreshListView refreshListView;
	private DropDownListView forScrollView;
	private CommentsAdapter commentsAdapter;
	//
	private Page page;
	private MyHandlerThread myHandlerThread;
	private static final String TAG = "CommentsActivity";
	/**
	 * 每页的评论数
	 */
	private static final int perPageCount = 30;
	/**
	 * 处理r
	 */
	private Handler mHandler;
	/**
	 * 评论数据源
	 */
	// private ArrayList<ItemBean> dataList = new ArrayList<ItemBean>();
	/**
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;
	/**
	 * 用户头像config
	 */
	public static DisplayImageOptions loginOptions;
	/**
	 * 图片下载器配置
	 */
	public static DisplayImageOptions contentOptions;
	private ItemBean itemBean = null;
	/**
	 * 异步获取数据
	 */
	private static AsyncHttpClient client = new AsyncHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coments_list_layout);

		initData();
		initViews();
	}

	/**
	 * 设置文章内容部分
	 */
	private void setArticleData(ItemBean itemBean) {
		// TODO Auto-generated method stub

		// 初始化图片缓存工具
		contentOptions = new DisplayImageOptions.Builder()

				.showStubImage(R.drawable.default_lazy_content_pic_loading)
				// .showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		loginOptions = new DisplayImageOptions.Builder()

		.showStubImage(R.drawable.default_users_avatar)
				.showImageForEmptyUri(R.drawable.default_users_avatar)
				.showImageOnFail(R.drawable.default_users_avatar)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		if (itemBean == null) {
			return;
		}
		UserBean userBean = itemBean.getUser();

		// 有图片则显示图片
		if (null != itemBean.getImage() && !itemBean.getImage().equals("")) {
			String articleImgUrl = AppConfig.ARTICLE_BIG_IMG
					+ itemBean.getId().substring(0, 4) + "/" + itemBean.getId()
					+ "/medium/" + itemBean.getImage();
			Log.i(TAG, articleImgUrl);
			imageLoader.displayImage(articleImgUrl, contentImg, contentOptions);

		}
		if (userBean != null) {
			tvUserLogin.setText(userBean.getLogin());
			String userImgUrl = AppConfig.USER_IMG
					+ userBean.getId().substring(0, 4) + "/" + userBean.getId()
					+ "/thumb/" + userBean.getIcon();

			Log.i(TAG, userImgUrl);
			imageLoader.displayImage(userImgUrl, userImg, loginOptions);
		}

		// 显示文章内容
		txtContent.setText(itemBean.getContent());

		// 显示文章发表时间
		tvPublishDate.setText(Common.getDateFromLong(
				itemBean.getPublished_at(), "yyyy-MM-dd HH:mm:ss"));
		// 显示支持数
		btnOptUp.setOperationInfo(String.valueOf(itemBean.getVotes().getUp()));
		// 显示不支持数
		btnOptDown.setOperationInfo(String.valueOf(itemBean.getVotes()
				.getDown()));
		// 显示评论数
		btnOptComments.setOperationInfo(String.valueOf(itemBean
				.getComments_count()));
		// 自动执行刷新
		forScrollView.onBottom();
		// // 获取数据之前先检查网络是否可用
		// if (Common.isNetConnected(CommentsActivity.this)) {
		//
		// page.setDefaultPage();
		// getCommentsData();
		// } else {
		//
		// ToastUtil.show(CommentsActivity.this, "当前网络不可用，建议检查网络连接状态。",
		// Toast.LENGTH_SHORT);
		// mHandler.sendEmptyMessage(MyHandlerThread.RESET_STATUS);
		// }
	}

	String responseStr;

	/**
	 * 获取评论数据
	 */
	private void getCommentsData() {

		String articleId = itemBean.getId();
		String url = String.format(AppConfig.DISCUSS_COTENT,
				Integer.parseInt(articleId), page.getCurrentPage(),
				perPageCount);
		Log.i(TAG, "请求数据url=====》" + url);
		// TODO Auto-generated method stub
		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// super.onFailure(statusCode, headers, responseString,
				// throwable);

				Toast.makeText(CommentsActivity.this, "获取数据失败!",
						Toast.LENGTH_SHORT).show();
				// 获取数据失败，页数需要减一
				page.minusPage();
				// 更新listview状态
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				// super.onSuccess(statusCode, headers, response);
				Log.i(TAG, response.toString());

				responseStr = response.toString();
				// 获取数据成功，刷新UI
				// mHandler.obtainMessage(MyHandlerThread.SUCCESS, responseStr);
				mHandler.sendEmptyMessage(MyHandlerThread.SUCCESS);

			}

		});

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		if (null != intent) {

			itemBean = (ItemBean) intent.getSerializableExtra("ItemBean");
		}
		if (null == itemBean) {

			itemBean = new ItemBean();
		}

	}

	/**
	 * 初始化视图
	 */
	private void initViews() {
		// TODO Auto-generated method stub

		myHandlerThread = new MyHandlerThread("HandlerThread");
		myHandlerThread.start();
		mHandler = new Handler(myHandlerThread.getLooper(), myHandlerThread);
		// 获取试图
		userImg = (CircleImageView) findViewById(R.id.user_img);
		tvUserLogin = (TextView) findViewById(R.id.tv_user_login);
		tvPublishDate = (TextView) findViewById(R.id.tv_publish_date);
		txtContent = (TextView) findViewById(R.id.content_txt);
		contentImg = (ImageView) findViewById(R.id.content_img);
		btnOptUp = (CustomImageButton) findViewById(R.id.btn_opt_up);
		btnOptDown = (CustomImageButton) findViewById(R.id.btn_opt_down);
		btnOptShare = (CustomImageButton) findViewById(R.id.btn_opt_share);
		btnOptShare.setOperationInfo("分享");
		btnOptComments = (CustomImageButton) findViewById(R.id.btn_opt_coments);
		forScrollView = (DropDownListView) findViewById(R.id.coments_listview);
		// forScrollView.addHeaderView(new ProgressDialog(this)) ;
		if (commentsAdapter == null) {

			commentsAdapter = new CommentsAdapter(CommentsActivity.this,
					new ArrayList<ItemBean>());
			forScrollView.setAdapter(commentsAdapter);
		}
		findViewById(R.id.back_img).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				finish();
				CommentsActivity.this.overridePendingTransition(
						R.anim.slide_in_left, R.anim.slide_out_right);
			}
		});
		// 设置末尾点击加载跟多
		forScrollView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				// 获取数据之前先检查网络是否可用
				if (Common.isNetConnected(CommentsActivity.this)) {
					forScrollView.onBottomBegin();
					page.addPage();
					getCommentsData();
				} else {

					ToastUtil.show(CommentsActivity.this,
							"当前网络不可用，建议检查网络连接状态。", Toast.LENGTH_SHORT);
					mHandler.sendEmptyMessage(MyHandlerThread.RESET_STATUS);
				}
			}
		});

		Resources rs = getResources();
		// 设置显示图片
		btnOptDown.setImageResource(rs.getDrawable(R.drawable.cai_not_clicked));
		btnOptUp.setImageResource(rs.getDrawable(R.drawable.ding_not_clicked));
		btnOptShare.setImageResource(rs.getDrawable(R.drawable.forward));
		btnOptComments.setImageResource(rs.getDrawable(R.drawable.commend));
		btnOptComments.setOperationInfo("分享");
		// 设置数据

		page = new Page();
		page.setCurrentPage(0);
		// 设置文章已有数据
		setArticleData(itemBean);

	}

	/**
	 * handlerThread处理数据
	 * 
	 * @author xiangxm
	 * 
	 */
	private class MyHandlerThread extends HandlerThread implements Callback {

		/**
		 * CommentsActivity.java 成功获取数据
		 */
		private static final int SUCCESS = 0x100;
		/**
		 * 恢复原状态
		 */
		private static final int RESET_STATUS = 0x101;
		/**
		 * 没有获取到数据
		 */
		private static final int NODATA_ARRIVED = 0x102;

		public MyHandlerThread(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub

			// String responseStr = (String) msg.obj;
			if (null == responseStr
					|| (null != responseStr && responseStr.equals(""))) {

				responseStr = "";
				msg.what = RESET_STATUS;
				mHandler.sendMessage(msg);
			}
			switch (msg.what) {

			case SUCCESS:
				final List<ItemBean> tempList = JSON.parseObject(
						responseStr.toString(), ArticleBean.class).getItems();

				if (tempList.size() == 0) {

					mHandler.sendEmptyMessage(MyHandlerThread.NODATA_ARRIVED);
					return false;
				}
				// dataList.addAll(tempList);
				final int num = tempList.size();
				CommentsActivity.this.runOnUiThread(new Runnable() {
					public void run() {

						// refresh UI
						if (tempList != null && !tempList.isEmpty()) {

							commentsAdapter
									.setDataList((ArrayList<ItemBean>) tempList);
							commentsAdapter.notifyDataSetChanged();
							// Crouton.showText(CommentsActivity.this, "刷新了" +
							// num
							// + "条评论.", Style.INFO, R.id.comments_layout);
							forScrollView.onBottomComplete();
						}
					}
				});

				break;

			case RESET_STATUS:

				CommentsActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						forScrollView.onBottomComplete();
					}
				});
				break;
			case NODATA_ARRIVED:

				CommentsActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						page.minusPage();
						forScrollView.setHasMore(false);
						forScrollView.onBottomComplete();

						// 没有更多了
						ToastUtil.show(getApplicationContext(), "没有更多了T_T",
								Toast.LENGTH_SHORT);
					}
				});
				break;

			}

			return false;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		CommentsActivity.this.overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}
}
