package com.qiubaiclient.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qiubaiclient.main.R;
import com.qiubaiclient.model.CategrySlideMenu;
import com.qiubaiclient.model.ItemSlideMenu;

/**
 * 菜单适配器
 * 
 * @author xiangxm
 * 
 */
public class SlideMenuAdapter extends BaseAdapter {

	private static final int ITEM_TYPE = 0;
	private static final int CATEGORY_TYPE = 1;

	private List<Object> itemList;
	private LayoutInflater layoutInflater;

	public SlideMenuAdapter(Context context) {

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		itemList = new ArrayList<Object>();
		setMenuList();
	}

	public void setMenuList() {
		itemList.clear();
		itemList.add(new CategrySlideMenu("工具"));

		itemList.add(new ItemSlideMenu("意见与建议", ItemSlideMenu.FEEDBACK_ID,
				R.drawable.sidebar_icon_send_feedback_dark));
		itemList.add(new ItemSlideMenu("关于", ItemSlideMenu.ABOUT_ID,
				R.drawable.sidebar_icon_rate_dark));
		itemList.add(new ItemSlideMenu("分享", ItemSlideMenu.SHARE_ID,
				R.drawable.sidebar_icon_share_dark));
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position) instanceof ItemSlideMenu ? ITEM_TYPE
				: CATEGORY_TYPE;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position) instanceof ItemSlideMenu;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		int type = getItemViewType(position);

		if (null == convertView || null == convertView.getTag(0x10086 + type)) {

			switch (type) {

			case ITEM_TYPE:
				convertView = layoutInflater.inflate(
						R.layout.sidemenu_list_item_item, parent, true);
				break;

			case CATEGORY_TYPE:
				convertView = layoutInflater.inflate(
						R.layout.sidemenu_list_item_category, parent, true);
				break;

			}

			viewHolder = buildHolder(convertView);
			convertView.setTag(0x10086 + type, viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag(0x10086 + type);
		}
		bindViewData(viewHolder, position);
		return null;
	}

	private ViewHolder buildHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.titleView = (TextView) convertView;
		return holder;
	}

	private void bindViewData(ViewHolder holder, final int position) {
		Object item = getItem(position);
		switch (getItemViewType(position)) {
		case ITEM_TYPE:
			holder.titleView.setText(((ItemSlideMenu) item).getItemTitle());
			holder.titleView.setCompoundDrawablesWithIntrinsicBounds(
					((ItemSlideMenu) item).getIcon(), 0, 0, 0);
			break;
		case CATEGORY_TYPE:
			holder.titleView.setText(((CategrySlideMenu) item).getCateName());
			break;

		default:
			break;
		}
	}

	/**
	 * ListView缓存类
	 * 
	 * @author xiangxm
	 * 
	 */
	private static final class ViewHolder {
		TextView titleView;
	}
}
