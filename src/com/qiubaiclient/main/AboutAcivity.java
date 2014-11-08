package com.qiubaiclient.main;

import com.way.ui.swipeback.SwipeBackActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 关于画面
 * 
 * @author xiangxm
 * 
 */
public class AboutAcivity extends SwipeBackActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about_activity_layout);
		TextView txtBlog = (TextView) findViewById(R.id.txt_personal_blog);
		TextView txtEmail = (TextView) findViewById(R.id.txt_personal_email);
		Linkify.addLinks(txtBlog, Linkify.ALL);
		Linkify.addLinks(txtEmail, Linkify.ALL);

		findViewById(R.id.about_back_imgview).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
						AboutAcivity.this.overridePendingTransition(
								R.anim.slide_in_left, R.anim.slide_out_right);
					}
				});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		AboutAcivity.this.overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

}
