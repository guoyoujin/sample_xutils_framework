package com.lidroid.xutils.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.custom.dialog.Dialog;
import com.lidroid.xutils.custom.dialog.Dialog.DialogClickListener;
import com.lidroid.xutils.custom.view.RoundCornerImageViewSmall;

public class GroupFourActivity extends BaseActivity {
	private TextView doctor_name;
	private TextView doctor_account;
	private RoundCornerImageViewSmall doctor_photo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_four);
		hideTitleView();
		initView();
	}
	
	public void initView(){
		doctor_name=(TextView) findViewById(R.id.doctor_name);
		doctor_account=(TextView) findViewById(R.id.doctor_account);
		doctor_photo=(RoundCornerImageViewSmall) findViewById(R.id.doctor_photo);
		findViewById(R.id.myselfmainphoto).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				
			}
		});
		findViewById(R.id.myselfmainsettings).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				
			}
		});
		findViewById(R.id.myselfmain_user_feedback).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				
			}
		});
		findViewById(R.id.myselfmaincontactservice).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {				
				Dialog.showSelectDialog(context, "确定拨打客服电话吗？", "010-82864722", new DialogClickListener() {					
					@Override
					public void confirm() {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_DIAL,
								 Uri.parse("tel:"
								 + "01082864722"));
								 startActivity(intent);
					}
					
					@Override
					public void cancel() {
						// TODO Auto-generated method stub
						
					}
				}, "取消", "确定");
			}
		});
		findViewById(R.id.myselfmainabouttx).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.tongxinyiliao.com");
				intent.setData(content_url);
				// intent.setClassName("com.android.browser",
				// "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});
	}
	

}
