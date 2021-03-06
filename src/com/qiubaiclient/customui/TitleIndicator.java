/*
 * @author http://blog.csdn.net/singwhatiwanna
 */
package com.qiubaiclient.customui;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiubaiclient.main.R;
import com.qiubaiclient.model.TabInfo;

/**
 * 这是个选项卡式的控件，会随着viewpager的滑动而滑动
 */
@SuppressWarnings("static-access")
public class TitleIndicator extends LinearLayout implements
		View.OnClickListener, OnFocusChangeListener {
	@SuppressWarnings("unused")
	private boolean DEBUG = false;

	@SuppressWarnings("unused")
	private static final String TAG = "TitleFlowIndicator";

	private static final int FOOTER_COLOR = 0xFFFFC445;

	private int mCurrentScroll = 0;

	// 选项卡列表
	private List<TabInfo> mTabs;

	// 选项卡所依赖的viewpager
	private ViewPager mViewPager;

	// 选项卡普通状态下的字体颜色
	private ColorStateList mTextColor;

	// 普通状态和选中状态下的字体大小
	private float mTextSizeNormal;
	private float mTextSizeSelected;

	private Path mPath = new Path();

	/**
	 * //当前选项卡的下标，从0开始
	 * 
	 */
	private int mSelectedTab = 0;

	private Context mContext;

	private final int BSSEEID = 0xffff00;;

	private boolean mChangeOnClick = true;

	private int mCurrID = 0;

	// 单个选项卡的宽度
	private int mPerItemWidth = 0;

	// 表示选项卡总共有几个
	private int mTotal = 0;

	private LayoutInflater mInflater;

	/**
	 * 圆点画笔
	 */
	private Paint mPaint;

	/**
	 * Default constructor
	 */
	public TitleIndicator(Context context) {
		super(context);
		initDraw(FOOTER_COLOR);
	}

	/**
	 * The contructor used with an inflater
	 * 
	 * @param context
	 * @param attrs
	 */
	public TitleIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setOnFocusChangeListener(this);
		mContext = context;
		// Retrieve styles attributs
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TitleIndicator);
		// Retrieve the colors to be used for this view and apply them.
		int footerColor = a.getColor(R.styleable.TitleIndicator_footerColor,
				FOOTER_COLOR);
		mTextColor = a.getColorStateList(R.styleable.TitleIndicator_textColor);
		mTextSizeNormal = a.getDimension(
				R.styleable.TitleIndicator_textSizeNormal, 0);
		mTextSizeSelected = a.getDimension(
				R.styleable.TitleIndicator_textSizeSelected, mTextSizeNormal);

		initDraw(footerColor);
		a.recycle();
	}

	/**
	 * Initialize draw objects
	 */
	private void initDraw(int footerColor) {
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	 * @这个是核心函数，选项卡是用canvas画出来的。所有的invalidate方法均会触发onDraw
	 * 大意是这样的：当页面滚动的时候，会有一个滚动距离，然后onDraw被触发后， 就会在新位置重新画上滚动条（其实就是画线）
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 下面是计算本次滑动的距离
		float scroll_x = 0;
		if (mTotal != 0) {
			mPerItemWidth = getWidth() / mTotal;
			int tabID = mSelectedTab;
			scroll_x = (mCurrentScroll - ((tabID) * (getWidth() + mViewPager
					.getPageMargin()))) / mTotal;
		} else {
			mPerItemWidth = getWidth();
			scroll_x = mCurrentScroll;
		}
		// 下面就是如何画线了
		Path path = mPath;
		path.rewind();
		float offset = 0;
		// 各个坐标
		float left_x = mSelectedTab * mPerItemWidth + offset + scroll_x;
		float bottom_y = getHeight() - 15;

		// 实心圆画笔
		mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setAntiAlias(true);
		canvas.drawCircle(left_x + mPerItemWidth / 2f, bottom_y, mContext
				.getResources().getDimension(R.dimen.circle_r), mPaint);
	}

	/**
	 * 获取指定下标的选项卡的标题
	 */
	private String getTitle(int pos) {
		// Set the default title
		String title = "title " + pos;
		// If the TitleProvider exist
		if (mTabs != null && mTabs.size() > pos) {
			title = mTabs.get(pos).getName();
		}
		return title;
	}

	private boolean hasTips(int pos) {
		boolean ret = false;
		if (mTabs != null && mTabs.size() > pos) {
			ret = mTabs.get(pos).hasTips;
		}
		return ret;
	}

	// 当页面滚动的时候，重新绘制滚动条
	public void onScrolled(int h) {
		mCurrentScroll = h;
		invalidate();
	}

	// 当页面切换的时候，重新绘制滚动条
	public synchronized void onSwitched(int position) {
		if (mSelectedTab == position) {
			return;
		}
		setCurrentTab(position);
		invalidate();
	}

	// 初始化选项卡
	public void init(int startPos, List<TabInfo> tabs, ViewPager mViewPager) {
		removeAllViews();
		this.mViewPager = mViewPager;
		this.mTabs = tabs;
		this.mTotal = tabs.size();
		for (int i = 0; i < mTotal; i++) {
			add(i, getTitle(i), hasTips(i));
		}
		setCurrentTab(startPos);
		invalidate();
	}

	public void updateChildTips(int postion, boolean showTips) {
		View child = getChildAt(postion);
		final ImageView tips = (ImageView) child
				.findViewById(R.id.tab_title_tips);
		if (showTips) {
			tips.setVisibility(View.VISIBLE);
		} else {
			tips.setVisibility(View.GONE);
		}
	}

	protected void add(int index, String label, boolean hasTips) {
		View tabIndicator;
		if (index < 2) {
			tabIndicator = mInflater.inflate(R.layout.title_flow_indicator,
					this, false);
		} else {
			tabIndicator = mInflater.inflate(R.layout.title_flow_indicator_v2,
					this, false);
		}
		final TextView tv = (TextView) tabIndicator
				.findViewById(R.id.tab_title);
		final ImageView tips = (ImageView) tabIndicator
				.findViewById(R.id.tab_title_tips);
		if (mTextColor != null) {
			tv.setTextColor(mTextColor);
		}
		if (mTextSizeNormal > 0) {
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeNormal);
		}
		tv.setText(label);
		tv.setAlpha(0.6f) ;

		if (hasTips) {
			tips.setVisibility(View.VISIBLE);
		} else {
			tips.setVisibility(View.GONE);
		}
		tabIndicator.setId(BSSEEID + (mCurrID++));
		tabIndicator.setOnClickListener(this);
		LinearLayout.LayoutParams lP = (LinearLayout.LayoutParams) tabIndicator
				.getLayoutParams();
		lP.gravity = Gravity.CENTER_VERTICAL;
		addView(tabIndicator);
	}

	public void setDisplayedPage(int index) {
		mSelectedTab = index;
	}

	public void setChangeOnClick(boolean changeOnClick) {
		mChangeOnClick = changeOnClick;
	}

	public boolean getChangeOnClick() {
		return mChangeOnClick;
	}

	@Override
	public void onClick(View v) {
		int position = v.getId() - BSSEEID;
		setCurrentTab(position);
	}

	public int getTabCount() {
		int children = getChildCount();
		return children;
	}

	// 设置当前选项卡
	public synchronized void setCurrentTab(int index) {
		if (index < 0 || index >= getTabCount()) {
			return;
		}
		View oldTab = getChildAt(mSelectedTab);
		oldTab.setSelected(false);
		setTabTextSize(oldTab, false);

		mSelectedTab = index;
		View newTab = getChildAt(mSelectedTab);
		newTab.setSelected(true);
		setTabTextSize(newTab, true);
		((ImageView) newTab.findViewById(R.id.tab_title_tips))
				.setVisibility(View.GONE);

		mViewPager.setCurrentItem(mSelectedTab);
		invalidate();
	}

	private void setTabTextSize(View tab, boolean selected) {
		TextView tv = (TextView) tab.findViewById(R.id.tab_title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, selected ? mTextSizeSelected
				: mTextSizeNormal);
		//设置透明度
		if (!selected) {

			tv.setAlpha(0.6f);
		} else {

			tv.setAlpha(1.0f);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v == this && hasFocus && getTabCount() > 0) {
			getChildAt(mSelectedTab).requestFocus();
			return;
		}

		if (hasFocus) {
			int i = 0;
			int numTabs = getTabCount();
			while (i < numTabs) {
				if (getChildAt(i) == v) {
					setCurrentTab(i);
					break;
				}
				i++;
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mCurrentScroll == 0 && mSelectedTab != 0) {
			mCurrentScroll = (getWidth() + mViewPager.getPageMargin())
					* mSelectedTab;
		}
	}
}
