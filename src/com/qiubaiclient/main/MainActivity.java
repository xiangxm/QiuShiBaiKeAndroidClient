package com.qiubaiclient.main;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.qiubaiclient.fragment.MenuFragment;
import com.qiubaiclient.fragment.QiuShiFragment;
import com.qiubaiclient.model.TabInfo;
import com.qiubaiclient.slidingmenu_library.SlidingMenu;
import com.qiubaiclient.utils.AppConfig;

/**
 * 主界面
 * @author xiangxm
 *
 */
public class MainActivity extends IndicatorFragmentActivity {

	/**
	 * SlidingMenu菜单
	 */
	SlidingMenu mSlidingMenu;
	/**
	 * 设置菜单
	 */
	MenuFragment menuFragment;
	/**
	 * Fragment管理
	 */
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mFragmentManager = this.getSupportFragmentManager();
		initSlidingMenu();
	}

	/**
	 * 配置SlidingMenu菜单
	 */
	private void initSlidingMenu() {

		int screenWidth = this.getWindowManager().getDefaultDisplay()
				.getWidth();

		setBehindContentView(R.layout.frame_menu);
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置左右滑动
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.drawer_shadow);
		mSlidingMenu.setBehindScrollScale(0.1f);// 设置拖拽效
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setBehindWidth(screenWidth / 3 * 2 + 30);
		mSlidingMenu.setFadeDegree(0.35F);// SlidingMenu滑动时的渐变程度);
		//设置菜单移动的速度相对于上面视图的移动速度
		mSlidingMenu.setBehindScrollScale(0.3f);
		// 设置触摸范围
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);// 设置阴影图片
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); 
		// 设置边界值
		if (menuFragment == null) {
			// 根据传递过来的id决定跳转到哪一块菜单去。
			// 设置 SlidingMenu 内容
			FragmentTransaction fragmentTransaction = mFragmentManager
					.beginTransaction();
			menuFragment = new MenuFragment();
			fragmentTransaction.replace(R.id.menu, menuFragment);
			fragmentTransaction.commit();
		}

	}

	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(AppConfig.SECTION_ONLY_TEXT,
				getString(R.string.fragment_one), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.SECTION_ONLY_IMAGE,
				getString(R.string.fragment_two), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.TEXT_AND_IMAGE,
				getString(R.string.fragment_three), QiuShiFragment.class));
		tabs.add(new TabInfo(AppConfig.SECTION_LATEST,
				getString(R.string.fragment_four), QiuShiFragment.class));

		return AppConfig.SECTION_ONLY_TEXT;
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

	@Override
	public void onScrollCheckPosition(int position) {
		// TODO Auto-generated method stub

		// 检查当前是否可以滚动，避免SlidingMenu和Fragment冲突
		if (position == 0) {// 第一页

			mSlidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_CONTENT);
		} else { // 中间页面
			mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

	}

}
