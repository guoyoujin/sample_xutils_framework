package com.lidroid.xutils.sample;

import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

public class ActivityGroupManager {
	public static final int RECORD_ACTIVITY_VIEW = 0;
	public static final int CATEGORY_ACTIVITY_VIEW = 1;
	public static final int MORE_AVTIVITY_VIEW = 2;
	public static final int FOUR_AVTIVITY_VIEW = 3;
	private HashMap<Integer, View> hashMap;
	private ViewGroup container;

	@SuppressLint("UseSparseArrays")
	public ActivityGroupManager() {
		hashMap = new HashMap<Integer, View>();
	}

	public void setContainer(ViewGroup container) {
		this.container = container;
	}
	
	public void showContainer(int num, View view) {
		if (!hashMap.containsKey(num)) {
			hashMap.put(num, view);
			container.addView(view);
		}

		for (Iterator<Integer> iter = hashMap.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			View v = hashMap.get(key);
			v.setVisibility(View.INVISIBLE);
		}
		
		view.setVisibility(View.VISIBLE);
	}
}
