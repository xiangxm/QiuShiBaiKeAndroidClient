package com.qiubaiclient.main;

import java.util.List;

import android.os.Bundle;

import com.qiubaiclient.fragment.FragQiuShi_MostHost;
import com.qiubaiclient.fragment.FragmentThree;
import com.qiubaiclient.fragment.FragmentTwo;
import com.qiubaiclient.main.R;

/**
 * just for test .
 * @author xiangxm
 *
 */
public class MainActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_one),
                FragQiuShi_MostHost.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_two),
                FragmentTwo.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_three),
                FragmentThree.class));
        tabs.add(new TabInfo(FRAGMENT_FOUR, getString(R.string.fragment_four),
                FragmentThree.class));
        return FRAGMENT_TWO;
    }

}
