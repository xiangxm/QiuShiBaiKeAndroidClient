package com.qiubaiclient.fragment;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.qiubaiclient.main.R;
import com.qiubaiclient.model.ArticleBean;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase.Mode;
import com.qiubaiclient.pulltorefresh.PullToRefreshListView;
import com.qiubaiclient.utils.Common;

/**
 * 
 * 糗事百科中模块：最热的部分
 * @author xiangxm
 *
 */
public class FragQiuShiMostHost extends BaseFragment {

	private static final String TAG = "FragQiuShi_MostHost" ;
	
	
	public FragQiuShiMostHost() {
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
		initView() ;
	}

	private PullToRefreshListView refreshListView;

	/**
	 * 初始化布局
	 */
	private void initView() {

		View view = getView();
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.content_listview);
		refreshListView.setMode(Mode.BOTH);
		refreshListView.setOnRefreshListener(this);

		String testStr = Common.readData(mContext, R.raw.json) ;
		
		try {
			String str = new String(testStr.getBytes(),"GBK") ;
			ArticleBean articleBean = JSON.parseObject(str, ArticleBean.class) ;
			Log.i(TAG, articleBean.getItems().toString()) ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i(TAG,testStr) ;
		
	}

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
