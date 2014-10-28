package com.qiubaiclient.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiubaiclient.customui.CircleImageView;
import com.qiubaiclient.customui.CustomImageButton;
import com.qiubaiclient.main.R;
import com.qiubaiclient.main.SaveImageActivity;
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
	private Resources rs;
	private ViewHolder viewHolder = null;
	/**
	 * 图片下载器
	 */
	public static ImageLoader imageLoader;
	/**
	 * 图片下载器配置
	 */
	public static DisplayImageOptions contentOptions;
	public static DisplayImageOptions loginOptions;

	public ArticleAdapter(Context mContext, List<ItemBean> dataList) {

		this.rs = mContext.getResources();
		this.dataList = dataList;
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 初始化图片缓存工具
		contentOptions = new DisplayImageOptions.Builder()

				.showStubImage(R.drawable.default_lazy_content_pic_loading)
				// .showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		loginOptions = new DisplayImageOptions.Builder()

				.showStubImage(R.drawable.default_users_avatar)
				// .showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
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
			viewHolder.txtPublishDate = (TextView) convertView
					.findViewById(R.id.tv_publish_date);
			viewHolder.btn_opt_coments = (CustomImageButton) convertView
					.findViewById(R.id.btn_opt_coments);
			viewHolder.btn_opt_down = (CustomImageButton) convertView
					.findViewById(R.id.btn_opt_down);
			viewHolder.btn_opt_share = (CustomImageButton) convertView
					.findViewById(R.id.btn_opt_share);
			viewHolder.btn_opt_up = (CustomImageButton) convertView
					.findViewById(R.id.btn_opt_up);

			// 设置显示图片
			viewHolder.btn_opt_down.setImageResource(this.rs
					.getDrawable(R.drawable.cai_not_clicked));
			viewHolder.btn_opt_up.setImageResource(this.rs
					.getDrawable(R.drawable.ding_not_clicked));
			viewHolder.btn_opt_share.setImageResource(this.rs
					.getDrawable(R.drawable.forward));
			viewHolder.btn_opt_coments.setImageResource(this.rs
					.getDrawable(R.drawable.commend));

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 先恢复状态
		resetStatus(viewHolder);
		ItemBean itemBean = (ItemBean) getItem(position);
		if (null != itemBean) {

			// 有图片则显示图片
			if (null != itemBean.getImage() && !itemBean.getImage().equals("")) {
				String articleImgUrl = AppConfig.ARTICLE_BIG_IMG
						+ itemBean.getId().substring(0, 4) + "/"
						+ itemBean.getId() + "/medium/" + itemBean.getImage();
				Log.i(TAG, articleImgUrl);
				// 设置tag
				viewHolder.contentImageView.setTag(itemBean);
				imageLoader.displayImage(articleImgUrl,
						viewHolder.contentImageView, contentOptions);
				viewHolder.contentImageView
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								ItemBean itemBean = (ItemBean) v.getTag();
								Intent intent = new Intent();
								intent.setClass(mContext,
										SaveImageActivity.class);
								intent.putExtra("itembean", itemBean);

								mContext.startActivity(intent);

							}
						});

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
				imageLoader.displayImage(userImgUrl, viewHolder.userImageView,
						loginOptions);
				viewHolder.txtLogin.setText((itemBean.getUser().getLogin()));
			} else {
				viewHolder.userImageView.setImageDrawable(rs
						.getDrawable(R.drawable.users_avatar_light));
				viewHolder.txtLogin.setText("糗百无名氏");
			}

			// 显示文章内容
			viewHolder.txtContent.setText(itemBean.getContent());

			// 显示文章发表时间
			viewHolder.txtPublishDate.setText(Common.getDateFromLong(
					itemBean.getPublished_at(), "yyyy-MM-dd HH:mm:ss"));
			// 显示支持数
			viewHolder.btn_opt_up.setOperationInfo(String.valueOf(itemBean
					.getVotes().getUp()));
			// viewHolder.btn_opt_up.setTag(position);
			// 显示不支持数
			viewHolder.btn_opt_down.setOperationInfo(String.valueOf(itemBean
					.getVotes().getDown()));
			// 显示评论数
			viewHolder.btn_opt_coments.setOperationInfo(String.valueOf(itemBean
					.getComments_count()));

			// 设置监听

			// viewHolder.btn_opt_up.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			//
			// int position = Integer.parseInt(Common
			// .object2String(viewHolder.btn_opt_up.getTag()));
			// ItemBean itemBean = dataList.get(position);
			// String numStr = String.valueOf(itemBean.getVotes().getUp()) + 1;
			// viewHolder.btn_opt_up.setOperationInfo(numStr);
			// viewHolder.btn_opt_up.setOptTextColor(Color
			// .parseColor("#F02D2B"));
			// viewHolder.btn_opt_up.setImageResource(rs
			// .getDrawable(R.drawable.ding_has_clicked));
			//
			// // 设置list中的数据
			// dataList.set(position, itemBean);
			// // 显示动画
			// viewHolder.btn_opt_up.startDingAnimation();
			// }
			// });

		}
		return convertView;
	}

	class ViewHolder {

		CircleImageView userImageView;
		ImageView contentImageView;

		TextView txtContent;
		TextView txtLogin;
		TextView txtPublishDate;

		CustomImageButton btn_opt_up;
		CustomImageButton btn_opt_down;
		CustomImageButton btn_opt_share;
		CustomImageButton btn_opt_coments;
	}

	/**
	 * 恢复状态
	 * 
	 * @param viewHolder
	 */
	public void resetStatus(ViewHolder viewHolder) {
		if (null == viewHolder) {

			return;
		}
		viewHolder.userImageView.setVisibility(View.VISIBLE);
		viewHolder.contentImageView.setVisibility(View.VISIBLE);
		viewHolder.btn_opt_coments.setOptTextColor(Color.parseColor("#777777"));
		viewHolder.btn_opt_down.setOptTextColor(Color.parseColor("#777777"));
		viewHolder.btn_opt_up.setOptTextColor(Color.parseColor("#777777"));
		viewHolder.btn_opt_up.setImageResource(rs
				.getDrawable(R.drawable.ding_not_clicked));
		viewHolder.btn_opt_down.setImageResource(rs
				.getDrawable(R.drawable.cai_not_clicked));
		viewHolder.btn_opt_coments.setImageResource(rs
				.getDrawable(R.drawable.commend));
		viewHolder.btn_opt_share.setImageResource(rs
				.getDrawable(R.drawable.forward));

	}

}
