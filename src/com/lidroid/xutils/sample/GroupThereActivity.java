package com.lidroid.xutils.sample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.bean.ResearchBean;
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

public class GroupThereActivity extends BaseActivity {
	private PullToRefreshListView xListView_reseash;
	private TextView add_research;
	public List<ResearchBean> resList=new ArrayList<ResearchBean>();
	private MyResearchAdaper adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_there);
		hideTitleView();
		initView();
		http_get_research();
	}	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				@SuppressWarnings("unchecked")
				ArrayList<ResearchBean> researchBeans=(ArrayList<ResearchBean>) msg.obj;
				if(researchBeans!=null&&researchBeans.size()>0){
					resList.clear();
					resList.addAll(researchBeans);
				}
				if(adapter==null){
					adapter=new MyResearchAdaper(getApplicationContext(), resList);
				}
				xListView_reseash.setAdapter(adapter);
				break;
			case 1:
				showToast("删除成功");
				http_get_research();
				break;
			case 2:
				showToast("删除失败");				
				break;
				
			case 3:
			
				break;
			default:
				break;
			}
		};
	};
	public void initView(){
		xListView_reseash=(PullToRefreshListView)findViewById(R.id.researchs_lv_data);
		xListView_reseash.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				
			}
		});

		// Add an end-of-list listener
		xListView_reseash.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				Toast.makeText(GroupThereActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		add_research=(TextView) findViewById(R.id.add_research);
	}
	private class MyResearchAdaper extends SimpleBaseAdapter<ResearchBean>{

		public MyResearchAdaper(Context context, List<ResearchBean> data) {
			super(context, data);
			
		}

		@Override
		public int getItemResource() {
			
			return R.layout.xlist_view_reseach;
		}

		@Override
		public View getItemView(int position,View convertView,ViewHolder holder) {
			JustifyTextView research_name = holder.getView(R.id.research_name);
			JustifyTextView research_patient_count = holder.getView(R.id.research_patient_count);
			research_name.setText(resList.get(position).getName());
			research_patient_count.setText(resList.get(position).getPatient_count()+"");
			return convertView;
		}
		
	}
	public void http_get_research(){
		RequestParams params=new RequestParams();
		params.addBodyParameter("remember_token", AppContext.REMEMBER_TOKEN);
		params.addBodyParameter("version",AppContext.version);
		params.addBodyParameter("sys",AppContext.sys);
		httpUtils.send(HttpMethod.GET,  AppContext.BASE_URL+AppContext.DOCROT_RESEARCH, params, new RequestCallBack<String>() {
	        public void onLoading(long total, long current, boolean isUploading) {
	            
	        }			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String doctor_patients=responseInfo.result;
				JSONObject jsonObject;
				ArrayList<ResearchBean> researchBeans=new ArrayList<ResearchBean>();
				try {
					jsonObject = new JSONObject(doctor_patients);
					if (jsonObject.optBoolean("success")){
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for(int i=0; i<jsonArray.length(); i++){
							ResearchBean researchBean =JSON.parseObject(jsonArray.getJSONObject(i).toString(), 
									ResearchBean.class);
							researchBeans.add(researchBean);
						}
						Message msg=new Message();
						msg.what=0;
						msg.obj=researchBeans;
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
	public void delete_research(String research_id){
		RequestParams params=new RequestParams();
		params.addBodyParameter("remember_token", AppContext.REMEMBER_TOKEN);
		params.addBodyParameter("version",AppContext.version);
		params.addBodyParameter("sys",AppContext.sys);
		params.addBodyParameter("id",research_id);
		httpUtils.send(HttpMethod.POST,  AppContext.BASE_URL+AppContext.DOCTOR_RESEARCH_DELETE, params, new RequestCallBack<String>() {
	        public void onLoading(long total, long current, boolean isUploading) {
	            
	        }			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String doctor_patients=responseInfo.result;
				JSONObject jsonObject;
				ArrayList<ResearchBean> researchBeans=new ArrayList<ResearchBean>();
				try {
					jsonObject = new JSONObject(doctor_patients);
					Message msg=new Message();
					if (jsonObject.optBoolean("success")){					
						msg.what=1;					
					}else{
						msg.what=2;
					}
					handler.sendMessage(msg);
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

}
