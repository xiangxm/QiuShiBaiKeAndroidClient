package com.qiubaiclient.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.qiubaiclient.pulltorefresh.PullToRefreshBase;
import com.qiubaiclient.pulltorefresh.PullToRefreshBase.OnRefreshListener2;

/**
 * 该类是所有Fragment的基类，所有公共的部分都在这里处理。
 * 
 * @author xiangxm
 * 
 */
public abstract class BaseFragment extends Fragment implements
		OnRefreshListener2<ListView> {
	private static final String TAG ="BaseFragment"  ;

	/**
	 * fragment绑定的上下文
	 */
	protected Context mContext;

	public BaseFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity.getApplicationContext();
	}

	/* (non-Javadoc)子类可能会调用
	 * @see com.qiubaiclient.pulltorefresh.PullToRefreshBase.OnRefreshListener2#onPullDownToRefresh(com.qiubaiclient.pulltorefresh.PullToRefreshBase)
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> mRefreshView) {
		// TODO Auto-generated method stub

		// 显示时间信息
		String label = DateUtils.formatDateTime(mContext,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		Log.i(TAG,"refresh time onPullDownToRefresh :" + label) ;
		mRefreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
	}

	/*刷新时间显示，子类可能会调用。
	 *  (non-Javadoc)
	 * @see com.qiubaiclient.pulltorefresh.PullToRefreshBase.OnRefreshListener2#onPullUpToRefresh(com.qiubaiclient.pulltorefresh.PullToRefreshBase)
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> mRefreshView) {
		// TODO Auto-generated method stub
		// 显示时间信息
		String label = DateUtils.formatDateTime(mContext,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		Log.i(TAG,"refresh time onPullUpToRefresh :" + label) ;
		mRefreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
	}

}
