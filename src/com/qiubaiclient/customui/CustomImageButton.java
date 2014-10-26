package com.qiubaiclient.customui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiubaiclient.main.R;
import com.qiubaiclient.utils.Common;

/**
 * 自定义带图片按钮
 * 
 * @author xiangxm
 * 
 */
public class CustomImageButton extends LinearLayout {

	public CustomImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.cust_imagebutton_layout,
				this, true);

		operationImageView = (ImageView) findViewById(R.id.operation_imgview);
		operationTxtInfo = (TextView) findViewById(R.id.operation_txt);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleImageView, defStyle, 0);

		Drawable resDrawable = a
				.getDrawable(R.styleable.CustomImageButton_background_resource);
		a.recycle();
		this.setBackgroundDrawable(resDrawable);
	}

	/**
	 * 左边图片
	 */
	private ImageView operationImageView;
	/**
	 * 右边文字
	 */
	private TextView operationTxtInfo;
	private Context mContext;
	/**
	 * +1数字
	 */
	private TextView txtDingAddOne;

	public CustomImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.cust_imagebutton_layout,
				this, true);

		operationImageView = (ImageView) findViewById(R.id.operation_imgview);
		operationTxtInfo = (TextView) findViewById(R.id.operation_txt);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleImageView);

		Drawable resDrawable = a
				.getDrawable(R.styleable.CustomImageButton_background_resource);
		a.recycle();
		this.setBackgroundDrawable(resDrawable);
	}

	public CustomImageButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置文字信息
	 * 
	 * @param infoStr
	 *            字符串资源
	 */
	public void setOperationInfo(String infoStr) {

		if (null != this.operationTxtInfo) {

			this.operationTxtInfo.setText(infoStr);
		}
	}

	/**
	 * 设置文字
	 * 
	 * @param infoRs
	 *            文字资源id
	 */
	public void setOperationInfo(int infoRs) {

		if (null != this.operationTxtInfo) {

			Resources rs = mContext.getResources();
			this.operationTxtInfo.setText(rs.getString(infoRs));
		}
	}

	/**
	 * 获取当前控件显示的文字信息
	 * 
	 * @return
	 */
	public String getOperationInfo() {

		if (null != operationTxtInfo) {

			return this.operationTxtInfo.getText().toString();
		}
		return "";

	}

	/**
	 * 设置字体颜色
	 * 
	 * @param color
	 *            颜色资源id
	 */
	public void setOptTextColor(int color) {

		if (null != this.operationTxtInfo) {

			this.operationTxtInfo.setTextColor(color);
		}

	}

	/**
	 * 十六进制颜色字符串
	 * 
	 * @param colorStr
	 */
	public void setOptTextColor(String colorStr) {

		if (null != this.operationTxtInfo) {

			this.operationTxtInfo.setTextColor(Color.parseColor(colorStr));
		}
	}

	/**
	 * 设置图片资源
	 * 
	 * @param bitmap
	 *            图片bitmap对象
	 */
	public void setImageResource(Bitmap bitmap) {

		if (null == bitmap) {

			return;
		}
		if (null != this.operationImageView) {

			this.operationImageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 设置图片
	 * 
	 * @param drawable
	 *            图片drawble对象
	 */
	public void setImageResource(Drawable drawable) {

		if (null == drawable) {

			return;
		}
		if (null != this.operationImageView) {

			Bitmap bitmap = Common.drawableToBitmap(drawable);
			this.operationImageView.setImageBitmap(bitmap);
		}
	}

}
