package com.lidroid.xutils.sample;

import com.lidroid.xutils.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GroupThereActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_there);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_there, menu);
		return true;
	}

}
