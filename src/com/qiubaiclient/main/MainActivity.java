package com.qiubaiclient.main;

import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.qiubaiclient.fragment.FragQiuShiMostHost;
import com.qiubaiclient.utils.AppConfig;

/**
 * just for test .
 * 
 * @author xiangxm
 * 
 */
public class MainActivity extends IndicatorFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(AppConfig.SECTION_MOST_HOT,
				getString(R.string.fragment_one), FragQiuShiMostHost.class));
		tabs.add(new TabInfo(AppConfig.SECTION_MOST_ESSONCE,
				getString(R.string.fragment_two), FragQiuShiMostHost.class));
		tabs.add(new TabInfo(AppConfig.SECTION_LATEST,
				getString(R.string.fragment_three), FragQiuShiMostHost.class));
		tabs.add(new TabInfo(AppConfig.SECTION_TRUTH,
				getString(R.string.fragment_four), FragQiuShiMostHost.class));
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
