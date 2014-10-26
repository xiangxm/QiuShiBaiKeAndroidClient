package com.qiubaiclient.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
		}
		// 设置监听
//		refreshListView.setOnItemClickListener(new MOnItemClickListener());

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
			String dataUrl = sectionAddress
					+ String.format(AppConfig.PAGE_COUNT,
							page.getCurrentPage(), perPageCount);
			Log.i(TAG, "请求数据url=====》" + dataUrl);
			getData(dataUrl);

		} else {

			ToastUtil.showShort(mContext, "当前网络不可用，建议检查网络连接状态。");
			// 恢复原始状态。
			if (refreshListView.isRefreshing()) {

				refreshListView.onRefreshComplete();
			}
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
			if (refreshListView.isRefreshing()) {

				refreshListView.onRefreshComplete();
			}
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
				List<ItemBean> tempList = JSON.parseObject(response.toString(),
						ArticleBean.class).getItems();
				dataList.addAll(tempList);
				if (dataList != null && !dataList.isEmpty()) {

					articleAdapter.setDataList(dataList);
					articleAdapter.notifyDataSetChanged();
					refreshListView.onRefreshComplete();
				}
			}

		});
	}

	/**
	 * ListView单独点击
	 * 
	 * @author xiangxm
	 * 
	 */
	class MOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			// position被点击的行数
			int clickPosition = position;
			ItemBean itemBean = dataList.get(clickPosition-1);

			if (null == itemBean) {

				return;
			}
		long id = 	adapter.getItemIdAtPosition(clickPosition) ;
			switch (view.getId()) {

			case R.id.content_img:

				String imgUrl = itemBean.getImage();
				if (null == imgUrl || imgUrl.equals("")) {

					return;
				}
				Intent intent = new Intent();
				intent.setClass(mContext, SaveImageActivity.class);
				intent.putExtra("articleImgUrl", imgUrl);
				mContext.startActivity(intent);
				break;

			case R.id.btn_opt_up:
				// ToastUtil.show(mContext, "你点击了按钮", Toast.LENGTH_SHORT);
				boolean bol = view instanceof CustomImageButton;
				if (!bol) {
					return;
				}
				String numStr = ((CustomImageButton) view).getOperationInfo();
				numStr = String.valueOf(Integer.parseInt(numStr) + 1);
				((CustomImageButton) view).setOperationInfo(numStr);
				((CustomImageButton) view).setOptTextColor(Color
						.parseColor("#F02D2B"));
				((CustomImageButton) view).setImageResource(mContext
						.getResources()
						.getDrawable(R.drawable.ding_has_clicked));
				break;
			case R.id.btn_opt_down:
				break;
			case R.id.btn_opt_coments:
				break;
			case R.id.btn_opt_share:
				break;
			}

		}
	}
}
