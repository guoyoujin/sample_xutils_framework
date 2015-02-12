package com.lidroid.xutils.sample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.bean.PatientBean;
import com.lidroid.xutils.config.AppContext;
import com.lidroid.xutils.custom.textview.JustifyTextView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.list.XListView;
import com.lidroid.xutils.simple.basead.apter.SimpleBaseAdapter;
import com.lidroid.xutils.util.LogUtils;

public class GroupOneActivity extends BaseActivity {
	private PullToRefreshListView patient_tab_list_view=null;
	private TestFoodListAdapter adapter=null;
	ArrayList<PatientBean> patient_list=new ArrayList<PatientBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_one);	
		hideTitleView();
		iniView ();
		http_get_doctor_Login();
	}
	
	private Handler handler=new Handler(){
		@SuppressWarnings("null")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				@SuppressWarnings("unchecked")
				ArrayList<PatientBean> patient=(ArrayList<PatientBean>) msg.obj;
				if(patient!=null&&patient.size()>0){
					patient_list.clear();
					patient_list.addAll(patient);
				}
				if(adapter==null){
					adapter=new TestFoodListAdapter(getApplicationContext(), patient_list);
				}
				patient_tab_list_view.onRefreshComplete();
				patient_tab_list_view.setAdapter(adapter);
				break;
			case 1:
				
				break;
				
			case 2:
				break;
			default:
				break;
			}
		};
	};
	@SuppressLint("NewApi")
	public void iniView (){
		patient_tab_list_view=(PullToRefreshListView) findViewById(R.id.patient_tab_list_view);
		patient_tab_list_view.setScrollingWhileRefreshingEnabled(!patient_tab_list_view
				.isScrollingWhileRefreshingEnabled());
		patient_tab_list_view.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				http_get_doctor_Login();
			}
		});

		// Add an end-of-list listener
		patient_tab_list_view.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				Toast.makeText(GroupOneActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
				http_get_doctor_Login();
			}
		});
	}
	
	public void http_get_doctor_Login(){
		RequestParams params=new RequestParams();
		params.addBodyParameter("remember_token", AppContext.REMEMBER_TOKEN);
		params.addBodyParameter("version",AppContext.version);
		LogUtils.e("remember_token="+AppContext.REMEMBER_TOKEN);
		params.addBodyParameter("sys",AppContext.sys);
		httpUtils.send(HttpMethod.GET,  AppContext.BASE_URL+AppContext.DOCROT_PATIENTS, params, new RequestCallBack<String>() {
	        public void onLoading(long total, long current, boolean isUploading) {
	            
	        }			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String doctor_patients=responseInfo.result;
				JSONObject jsonObject;
				List<PatientBean> list_patient=new ArrayList<PatientBean>();
				try {
					LogUtils.e(doctor_patients);
					jsonObject = new JSONObject(doctor_patients);
					if (jsonObject.optBoolean("success")){
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for(int i=0; i<jsonArray.length(); i++){
							PatientBean patient =JSON.parseObject(jsonArray.getJSONObject(i).toString(), 
									PatientBean.class);
							list_patient.add(patient);
						}
						Message msg=new Message();
						msg.obj=list_patient;
						msg.what=0;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				LogUtils.e(doctor_patients);
			}
			@Override
			public void onFailure(HttpException error, String msg) {			
				
			}
		});
	}
	public class TestFoodListAdapter extends SimpleBaseAdapter<PatientBean> {		
	    public TestFoodListAdapter(Context context, List<PatientBean> data) {
	        super(context, data);
	    }
	    @Override
	    public int getItemResource() {
	        return R.layout.patient_listview_item;
	    }
	    @Override
	    public View getItemView(int position, View convertView, ViewHolder holder) {
	        JustifyTextView text = holder.getView(R.id.patient_list_view_tv);
	        text.setText(patient_list.get(position).getName());
	        ImageView imageView=holder.getView(R.id.patient_list_view_img);
	        bitmapUtils.display(imageView,patient_list.get(position).getPhoto_thumb_path(), bigPicDisplayConfig, bitmapLoadCallBack);
	        return convertView;
	    }
	}

}
