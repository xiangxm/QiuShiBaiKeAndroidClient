package com.qiubaiclient.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiubaiclient.adapter.ArticleAdapter;
import com.qiubaiclient.customui.CustomImageButton;
import com.qiubaiclient.main.R;
import com.qiubaiclient.main.SaveImageActivity;
import com.qiubaiclient.model.ArticleBean;
import com.qiubaiclient.model.ItemBean;
import com.qiubaiclient.model.Page;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase.Mode;
import com.qiubaiclient.pulltorefresh.PullToRefreshListView;
import com.qiubaiclient.utils.AppConfig;
import com.qiubaiclient.utils.Common;
import com.qiubaiclient.utils.ToastUtil;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 
 * 糗事百科中模块：最热的部分
 * 
 * @author xiangxm
 * 
 */
public class FragQiuShiMostHost extends BaseFragment {

	private static final String TAG = "FragQiuShi_MostHost";

	/**
	 * 页数封装对象
	 */
	private Page page;
	/**
	 * 平均每一页有多少条
	 */
	private static final int perPageCount = 30;
	/**
	 * listView数据源
	 */
	private List<ItemBean> dataList;
	/**
	 * 文章适配器
	 */
	private ArticleAdapter articleAdapter;

	/**
	 * 模块对应的数据地址。
	 */
	private String sectionAddress;

	/**
	 * 是否已经加载过数据
	 */
	private boolean isLoadData = false;

	/**
	 * 下拉和上拉刷新
	 */
	private PullToRefreshListView refreshListView;
	/**
	 * 异步获取数据
	 */
	private static AsyncHttpClient client = new AsyncHttpClient();

	/**
	 * 处理r
	 */
	private Handler mHandler;

	private MyHandlerThread myHandlerThread;

	private String responseStr;

	/**
	 * handlerThread处理数据
	 * 
	 * @author xiangxm
	 * 
	 */
	private class MyHandlerThread extends HandlerThread implements Callback {

		/**
		 * 成功获取数据
		 */
		private static final int SUCCESS = 0x100;
		/**
		 * 恢复原状态
		 */
		private static final int RESET_STATUS = 0x101;

		public MyHandlerThread(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch (msg.what) {

			case SUCCESS:
				List<ItemBean> tempList = JSON.parseObject(
						responseStr.toString(), ArticleBean.class).getItems();

				dataList.addAll(tempList);
				final int num = tempList.size();
				((Activity) mContext).runOnUiThread(new Runnable() {
					public void run() {

						// refresh UI
						if (dataList != null && !dataList.isEmpty()) {

							articleAdapter.setDataList(dataList);
							articleAdapter.notifyDataSetChanged();
							refreshListView.onRefreshComplete();
							Crouton.showText((Activity) mContext, "刷新了" + num
									+ "条糗事.", Style.INFO,R.id.container_view);
						}
					}
				});

				break;

			case RESET_STATUS:

				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						// 停止刷新
						if (refreshListView.isRefreshing()) {

							refreshListView.onRefreshComplete();
						}
					}
				});
				break;

			}

			return false;
		}

	}

	public FragQiuShiMostHost() {
		super();
	}

	/**
	 * 设定版块获取数据地址
	 */
	public void setSectionName(int sectionId) {
		// TODO Auto-generated method stub

		switch (sectionId) {

		case AppConfig.SECTION_MOST_HOT:
			// 最热
			sectionAddress = AppConfig.MOST_HOT;
			break;

		case AppConfig.SECTION_MOST_ESSONCE:
			// 精华
			sectionAddress = AppConfig.MOST_ESSONCE;
			break;

		case AppConfig.SECTION_LATEST:
			// 最新
			sectionAddress = AppConfig.LATEST;
			break;

		case AppConfig.SECTION_TRUTH:
			// 真相
			sectionAddress = AppConfig.TRUTH;
			break;

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 获取xml视图。
		return inflater.inflate(R.layout.fragment_with_listview_layout,
				container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView();
	}

	/**
	 * 初始化布局
	 */
	private void initView() {

		myHandlerThread = new MyHandlerThread("HandlerThread");
		myHandlerThread.start();
		mHandler = new Handler(myHandlerThread.getLooper(), myHandlerThread);

		View view = getView();
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.content_listview);
		refreshListView.setMode(Mode.BOTH);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setAdapter(articleAdapter);

		page.setDefaultPage();
		// 获取数据之前先检查网络是否可用
		if (Common.isNetConnected(mContext)) {

			if (!isLoadData) {

				isLoadData = true;
				refreshListView.setRefreshing(true);// 显示或者隐藏进度view
			}
		} else {

			ToastUtil.show(mContext, "当前网络不可用，建议检查网络连接状态。", Toast.LENGTH_SHORT);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		page = new Page();
		dataList = new ArrayList<ItemBean>();
		articleAdapter = new ArticleAdapter(mContext, dataList);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		super.onPullDownToRefresh(refreshView);
		if (Common.isNetConnected(mContext)) {

			// 向下刷新默认获取第一页的数据
			page.setDefaultPage();
			dataList.clear();
			String dataUrl = sectionAddress
					+ String.format(AppConfig.PAGE_COUNT,
							page.getCurrentPage(), perPageCount);
			Log.i(TAG, "请求数据url=====》" + dataUrl);
			getData(dataUrl);

		} else {

			ToastUtil.showShort(mContext, "当前网络不可用，建议检查网络连接状态。");
			// 恢复原始状态。这里必须要使用handler来处理，否则没有效果。
			mHandler.sendEmptyMessage(MyHandlerThread.RESET_STATUS);

		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		super.onPullUpToRefresh(refreshView);
		if (Common.isNetConnected(mContext)) {

			// 向上刷新页数增加一页
			page.addPage();
			String dataUrl = sectionAddress
					+ String.format(AppConfig.PAGE_COUNT,
							page.getCurrentPage(), perPageCount);
			Log.i(TAG, "请求数据url=====》" + dataUrl);
			getData(dataUrl);

		} else {

			ToastUtil.showShort(mContext, "当前网络不可用，建议检查网络连接状态。");
			// 恢复原始状态。
			mHandler.sendEmptyMessage(MyHandlerThread.RESET_STATUS);
		}

	}

	/**
	 * 获取数据
	 */
	private void getData(String url) {

		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// super.onFailure(statusCode, headers, responseString,
				// throwable);

				Toast.makeText(mContext, "获取数据失败!", Toast.LENGTH_SHORT).show();
				// 获取数据失败，页数需要减一
				page.minusPage();
				// 更新listview状态
				refreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				// super.onSuccess(statusCode, headers, response);
				Log.i(TAG, response.toString());

				responseStr = response.toString();
				// 获取数据成功，刷新UI
				mHandler.sendEmptyMessage(MyHandlerThread.SUCCESS);

			}

		});
	}
}
