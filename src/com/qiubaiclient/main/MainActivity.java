package com.qiubaiclient.main;

import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.MenuDrawer.OnDrawerStateChangeListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qiubaiclient.adapter.SlideMenuAdapter;
import com.qiubaiclient.fragment.QiuShiFragment;
import com.qiubaiclient.utils.AppConfig;
import com.qiubaiclient.utils.Common;

/**
 * just for test .
 * 
 * @author xiangxm
 * 
 */
public class MainActivity extends IndicatorFragmentActivity {

	/**
	 * 侧边滑动菜单
	 */
	private MenuDrawer menuDrawer;
	private ListView menuListView;
	private SlideMenuAdapter menuAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		initMenuDrawer();
	}

	/**
	 * 初始化侧边菜单
	 */
	private void initMenuDrawer() {
		// TODO Auto-generated method stub

		menuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY);
		menuDrawer.setMenuSize(Math.round(0.6f * Common.getDisplayWidth(this)));

		View view = LayoutInflater.from(this).inflate(
				R.layout.sidemenu_listview, null, false);
		menuListView = (ListView) view.findViewById(R.id.menu_list);
		menuDrawer.setMenuView(menuListView);
		menuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_BEZEL);

		menuDrawer
				.setOnInterceptMoveEventListener(new MenuDrawer.OnInterceptMoveEventListener() {
					@Override
					public boolean isViewDraggable(View v, int dx, int x, int y) {
						if (v == mPager) {
							return !(mCurrentTab == 0 && mPagerOffsetPixels == 0)
									|| dx < 0;
						}
						return false;
					}
				});
		menuDrawer
				.setOnDrawerStateChangeListener(new OnDrawerStateChangeListener() {

					@Override
					public void onDrawerStateChange(int oldState, int newState) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onDrawerSlide(float openRatio, int offsetPixels) {
						// TODO Auto-generated method stub
						// changeBlurImageViewAlpha(openRatio);
					}
				});
		menuAdapter = new SlideMenuAdapter(this);
		menuListView.setAdapter(menuAdapter);
		// menuListView.setOnItemClickListener(mItemClickListener);
	}

	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(AppConfig.SECTION_MOST_HOT,
				getString(R.string.fragment_one), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.SECTION_MOST_ESSONCE,
				getString(R.string.fragment_two), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.SECTION_LATEST,
				getString(R.string.fragment_three), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.SECTION_TRUTH,
				getString(R.string.fragment_four), QiuShiFragment.class));
		return AppConfig.SECTION_MOST_HOT;
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
