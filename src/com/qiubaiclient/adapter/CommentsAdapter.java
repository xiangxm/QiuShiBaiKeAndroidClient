package com.qiubaiclient.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiubaiclient.customui.CircleImageView;
import com.qiubaiclient.main.R;
import com.qiubaiclient.model.ItemBean;
import com.qiubaiclient.model.UserBean;
import com.qiubaiclient.utils.AppConfig;
import com.qiubaiclient.utils.Common;
import com.tencent.a.b.m;

/**
 * 评论列表自定义Adapter
 * 
 * @author xiangxm
 * 
 */
public class CommentsAdapter extends BaseAdapter {

	private static final String TAG = "CommentsAdapter";
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<ItemBean> dataList;
	/**
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;
	/**
	 * 用户头像config
	 */
	public static DisplayImageOptions loginOptions;
	private Resources rs;

	public CommentsAdapter(Context mContext, ArrayList<ItemBean> dataList) {

		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dataList = new ArrayList<ItemBean>(dataList);
		rs = mContext.getResources();
		initImageLoader();
	}

	public void setDataList(ArrayList<ItemBean> dataList) {

		this.dataList.addAll(dataList);
	}

	private void initImageLoader() {
		// TODO Auto-generated method stub
		loginOptions = new DisplayImageOptions.Builder()

				.showStubImage(R.drawable.default_users_avatar)
				// .showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
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
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (null == convertView) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.coments_listview_item_layout, parent, false);
			viewHolder.commentUserImg = (CircleImageView) convertView
					.findViewById(R.id.coment_userimg);
			viewHolder.commentUserName = (TextView) convertView
					.findViewById(R.id.coment_txt_username);
			viewHolder.comment = (TextView) convertView
					.findViewById(R.id.comments_content);
			viewHolder.commentCreatTime = (TextView) convertView
					.findViewById(R.id.coment_txt_create_time);
			viewHolder.commentLight = (TextView) convertView
					.findViewById(R.id.coments_txt_lights);

			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		ItemBean itemBean = (ItemBean) getItem(position);

		if (null != itemBean) {

			// 设置头像
			UserBean user = itemBean.getUser();
			if (user != null) {

				String userImgUrl = AppConfig.USER_IMG
						+ user.getId().substring(0, 4) + "/" + user.getId()
						+ "/thumb/" + user.getIcon();

				Log.i(TAG, userImgUrl);
				imageLoader.displayImage(userImgUrl, viewHolder.commentUserImg,
						loginOptions);
				viewHolder.commentUserName.setText((itemBean.getUser()
						.getLogin()));
			} else {
				viewHolder.commentUserImg.setImageDrawable(rs
						.getDrawable(R.drawable.users_avatar_light));
				viewHolder.commentUserName.setText("糗百无名氏");
			}

			viewHolder.comment.setText(itemBean.getContent());
			UserBean userBean = itemBean.getUser();
			if (userBean != null && userBean.getCreated_at() != null) {

				viewHolder.commentCreatTime.setText(Common
						.getDateFromLong(userBean.getCreated_at(),"yyyy-MM-dd"));
			} else {

				viewHolder.commentCreatTime.setText("");
			}
			viewHolder.commentLight.setText(String.valueOf(position+1) + "L");

		}

		return convertView;
	}

	class ViewHolder {

		CircleImageView commentUserImg;
		TextView commentUserName;
		TextView comment;
		TextView commentCreatTime;
		TextView commentLight;

	}

}
