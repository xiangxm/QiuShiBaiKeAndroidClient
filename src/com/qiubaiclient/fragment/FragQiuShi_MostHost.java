package com.qiubaiclient.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qiubaiclient.main.R;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase.Mode;
import com.qiubaiclient.pulltorefresh.PullToRefreshListView;

/**
 * 
 * 糗事百科中模块：最热的部分
 * @author xiangxm
 *
 */
public class FragQiuShi_MostHost extends BaseFragment {

	protected static ArrayList<Map<String, Object>> mlistItems;

	public FragQiuShi_MostHost() {
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
		 initView() ;
		super.onViewCreated(view, savedInstanceState);
	}

	private PullToRefreshListView refreshListView;

	/**
	 * 初始化布局
	 */
	private void initView() {

		// TODO Auto-generated method stub

		View view = getView();
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.content_listview);
		refreshListView.setMode(Mode.BOTH);
		refreshListView.setOnRefreshListener(this);
		SimpleAdapter adapter = new SimpleAdapter(mContext, mlistItems,
				R.layout.listview_item, new String[] { "name", "sex" },
				new int[] { R.id.name, R.id.download });
		refreshListView.setAdapter(adapter);

	}

//	static {
//		mlistItems = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < 20; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("name", "name#" + i);
//			map.put("sex", i % 2 == 0 ? "male" : "female");
//			mlistItems.add(map);
//		}
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		super.onPullDownToRefresh(refreshView);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		super.onPullUpToRefresh(refreshView);
	}
	
	

}
