package com.qiubaiclient.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiubaiclient.adapter.ArticleAdapter;
import com.qiubaiclient.main.R;
import com.qiubaiclient.model.ArticleBean;
import com.qiubaiclient.model.ItemBean;
import com.qiubaiclient.model.Page;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase.Mode;
import com.qiubaiclient.pulltorefresh.PullToRefreshListView;
import com.qiubaiclient.utils.AppConfig;

/**
 * 
 * 糗事百科中模块：最热的部分
 * 
 * @author xiangxm
 * 
 */
public class FragQiuShiMostHost extends BaseFragment {

	private static final String TAG = "FragQiuShi_MostHost";

	// private String dataUrl;
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
	 * 模块id
	 */
	private int sectionId = -1;

	/**
	 * 是否已经加载过数据
	 */
	private boolean isLoadData = false;

	/**
	 * 板块
	 * 
	 * @author xiangxm
	 * 
	 */
	enum SECTION {

		MOST_HOT, MOST_ESSONCE, LATEST, TRUTH

	}

	/**
	 * 下拉和上拉刷新
	 */
	private PullToRefreshListView refreshListView;
	/**
	 * 异步获取数据
	 */
	private static AsyncHttpClient client = new AsyncHttpClient();

	public FragQiuShiMostHost() {
		super();
	}

	public FragQiuShiMostHost(int sectionId) {
		super();

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

		View view = getView();
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.content_listview);
		refreshListView.setMode(Mode.BOTH);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setAdapter(articleAdapter);

		if (!isLoadData) {

			isLoadData = true;
			refreshListView.setRefreshing(true);// 显示或者隐藏进度view
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
		String dataUrl = AppConfig.MOST_HOT
				+ String.format(AppConfig.PAGE_COUNT, page.getDefaultPage(),
						perPageCount);
		getData(dataUrl);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		super.onPullUpToRefresh(refreshView);
	}

	/**
	 * 获取数据
	 */
	private void getData(String url) {

		Log.i(TAG, "请求数据url=====》" + url);

		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				// super.onFailure(statusCode, headers, responseString,
				// throwable);

				Toast.makeText(mContext, "失败了", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				// super.onSuccess(statusCode, headers, response);
				Log.i(TAG, response.toString());
				dataList = JSON.parseObject(response.toString(), ArticleBean.class).getItems() ;
				if (dataList != null) {

					articleAdapter.setDataList(dataList);
					refreshListView.setAdapter(articleAdapter);
					articleAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				// super.onSuccess(statusCode, headers, responseString);

			}

		});

	}

}
