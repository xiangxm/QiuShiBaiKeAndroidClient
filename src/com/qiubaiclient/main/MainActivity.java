package com.qiubaiclient.main;

import java.util.List;

import android.os.Bundle;

import com.qiubaiclient.fragment.FragQiuShiMostHost;
import com.qiubaiclient.utils.AppConfig;

/**
 * just for test .
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
        tabs.add(new TabInfo(AppConfig.SECTION_MOST_HOT, getString(R.string.fragment_one),
                FragQiuShiMostHost.class));
        tabs.add(new TabInfo(AppConfig.SECTION_MOST_ESSONCE, getString(R.string.fragment_two),
        		FragQiuShiMostHost.class));
        tabs.add(new TabInfo(AppConfig.SECTION_LATEST, getString(R.string.fragment_three),
        		FragQiuShiMostHost.class));
        tabs.add(new TabInfo(AppConfig.SECTION_TRUTH, getString(R.string.fragment_four),
        		FragQiuShiMostHost.class));
        return AppConfig.SECTION_MOST_HOT;
    }

}
