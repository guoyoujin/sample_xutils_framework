package com.lidroid.xutils.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import com.lidroid.xutils.BaseActivity;

public class LoadingFristActivity extends BaseActivity {
	private boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_loading_frist);
		hideTitleView();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				initIntent();
			}
		}, 1000);// 2000为间隔的时间-毫秒		
	}

	public void initIntent(){
		if(false){
			this.startActivity(new Intent(LoadingFristActivity.this, LoginGuideFrist.class));
			this.finish();
		}else{
			startActivity(new Intent(LoadingFristActivity.this, LoginGuideFrist.class));
			finish();
		}
	}
	
}
