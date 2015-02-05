package com.lidroid.xutils.sample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.bean.PatientBean;
import com.lidroid.xutils.config.AppContext;
import com.lidroid.xutils.custom.textview.JustifyTextView;
import com.lidroid.xutils.custom.view.ToastView;
import com.lidroid.xutils.custom.view.XListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.simple.basead.apter.SimpleBaseAdapter;
import com.lidroid.xutils.util.LogUtils;

public class GroupOneActivity extends BaseActivity {
	private XListView patient_tab_list_view=null;
	ArrayList<PatientBean> patient_list=new ArrayList<PatientBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_one);	
		hideTitleView();
		iniView ();
		http_get_doctor_Login();
	}
	
	
	public void iniView (){
		patient_tab_list_view=(XListView) findViewById(R.id.patient_tab_list_view);
	}
	
	public void http_get_doctor_Login(){
		RequestParams params=new RequestParams();
		params.addBodyParameter("remember_token", AppContext.REMEMBER_TOKEN);
		params.addBodyParameter("version",AppContext.version);
		params.addBodyParameter("version",AppContext.version);
		httpUtils.send(HttpMethod.GET,  AppContext.BASE_URL+AppContext.DOCROT_PATIENTS, params, new RequestCallBack<String>() {
	        public void onLoading(long total, long current, boolean isUploading) {
	            
	        }			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String doctor_patients=responseInfo.result;
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(doctor_patients);
					if (jsonObject.optBoolean("success")){
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for(int i=0; i<jsonArray.length(); i++){
							PatientBean medicalRecordBean =JSON.parseObject(jsonArray.getJSONObject(i).toString(), 
									PatientBean.class);
							patient_list.add(medicalRecordBean);
						}
						TestFoodListAdapter adapter=new TestFoodListAdapter(getApplicationContext(), patient_list);
						patient_tab_list_view.setAdapter(adapter);
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
