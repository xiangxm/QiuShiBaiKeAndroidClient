package com.qiubaiclient.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiubaiclient.app.MyApplication;
import com.qiubaiclient.customui.CircleImageView;
import com.qiubaiclient.main.R;
import com.qiubaiclient.model.ItemBean;
import com.qiubaiclient.model.UserBean;
import com.qiubaiclient.utils.AppConfig;
import com.qiubaiclient.utils.Common;

/**
 * 自定义文章列表适配器
 * 
 * @author xiangxm
 * 
 */
public class ArticleAdapter extends BaseAdapter {

	private static final String TAG = "ArticleAdapter";
	private List<ItemBean> dataList;
	private Context mContext;
	private LayoutInflater inflater;

	public ArticleAdapter(Context mContext, List<ItemBean> dataList) {

		this.dataList = dataList;
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public List<ItemBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ItemBean> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (null == convertView) {

			convertView = this.inflater.inflate(
					R.layout.content_item_img_layout, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.userImageView = (CircleImageView) convertView
					.findViewById(R.id.user_img);
			viewHolder.txtLogin = (TextView) convertView
					.findViewById(R.id.tv_user_login);
			viewHolder.contentImageView = (ImageView) convertView
					.findViewById(R.id.content_img);
			viewHolder.txtContent = (TextView) convertView
					.findViewById(R.id.content_txt);
			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.userImageView.setVisibility(View.VISIBLE);
		viewHolder.contentImageView.setVisibility(View.VISIBLE);
		ItemBean itemBean = (ItemBean) getItem(position);
		if (null != itemBean) {

			// 有图片则显示图片
			if (null != itemBean.getImage() && !itemBean.getImage().equals("")) {
				String articleImgUrl = AppConfig.ARTICLE_BIG_IMG
						+ itemBean.getId().substring(0, 4) + "/"
						+ itemBean.getId() + "/medium/" + itemBean.getImage();
				Log.i(TAG, articleImgUrl);
				com.qiubaiclient.app.MyApplication.imageLoader.displayImage(
						articleImgUrl, viewHolder.contentImageView,
						MyApplication.options);

			} else {

				viewHolder.contentImageView.setVisibility(View.GONE);
			}
			// 设置头像
			UserBean user = itemBean.getUser();
			if (user != null) {

				String userImgUrl = AppConfig.USER_IMG
						+ user.getId().substring(0, 4) + "/" + user.getId()
						+ "/thumb/" + user.getIcon();

				Log.i(TAG, userImgUrl);
				com.qiubaiclient.app.MyApplication.imageLoader.displayImage(
						userImgUrl, viewHolder.userImageView,
						MyApplication.options);
				viewHolder.txtLogin.setText((itemBean
						.getUser().getLogin()));
			} else {

				viewHolder.userImageView.setVisibility(View.GONE);
				viewHolder.txtLogin.setText("糗百大神");
			}

			// 显示文章内容
			viewHolder.txtContent.setText(itemBean
					.getContent());

		}
		return convertView;
	}

	// I added a generic return type to reduce the casting noise in client
	// code
	// @SuppressWarnings("unchecked")
	// public static <T extends View> T get(View view, int id) {
	// SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
	// if (viewHolder == null) {
	// viewHolder = new SparseArray<View>();
	// view.setTag(viewHolder);
	// }
	// View childView = viewHolder.get(id);
	// if (childView == null) {
	// childView = view.findViewById(id);
	// viewHolder.put(id, childView);
	// }
	// return (T) childView;
	// }

	class ViewHolder {

		CircleImageView userImageView;
		ImageView contentImageView;

		TextView txtContent;
		TextView txtLogin;

	}

}
