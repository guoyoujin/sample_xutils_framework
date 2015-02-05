package com.lidroid.xutils.sample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.gson.JsonObject;
import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.config.AppContext;
import com.lidroid.xutils.custom.view.FButton;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

public class LoginGuideFrist extends BaseActivity implements OnClickListener{
	private ViewPager viewPager_login_guide_frist;
	@SuppressWarnings("unused")
	private FButton btn_login,btn_register=null;
	@SuppressWarnings("unused")
	private ImageButton btn_tiyanmoshi_click=null;
	@SuppressWarnings("unused")
	private ImageView imageView_view_pager=null;
	private int[] mImgIds = new int[] { R.drawable.doctorguide0,R.drawable.doctorguide1,
			R.drawable.doctorguide2, R.drawable.doctorguide3 };
	private List<ImageView> mImageViews = new ArrayList<ImageView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_login_guide_frist);
		hideTitleView();
		initData();
		initView ();
		initPagerView();
		
	}
	@SuppressLint("ResourceAsColor")
	public void initView (){
		viewPager_login_guide_frist=(ViewPager) findViewById(R.id.viewPager_login_guide_frist);
		btn_login=(FButton) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		btn_login.setCornerRadius(10);
		btn_login.setButtonColor(getResources().getColor(R.color.login_delung));
		btn_login.setShadowColor(getResources().getColor(R.color.login_show));
		btn_login.setShadowEnabled(false);
		btn_register=(FButton) findViewById(R.id.btn_register);
		btn_register.setCornerRadius(10);
		btn_register.setShadowEnabled(false);
		btn_register.setButtonColor(getResources().getColor(R.color.actionbar_btn_text_normal));
		btn_register.setShadowColor(getResources().getColor(R.color.login_register));
		btn_register.setOnClickListener(this);
		btn_tiyanmoshi_click=(ImageButton) findViewById(R.id.btn_tiyanmoshi_click);
		btn_tiyanmoshi_click.setOnClickListener(this);
		imageView_view_pager=(ImageView)findViewById(R.id.imageView_view_pager);
	}
	public void initPagerView(){
		viewPager_login_guide_frist.setAdapter(new PagerAdapter(){   
            public Object instantiateItem(ViewGroup container, int position){    
                container.addView(mImageViews.get(position));  
                return mImageViews.get(position);  
            }    
            public void destroyItem(ViewGroup container, int position,Object object){    
                container.removeView(mImageViews.get(position));  
            }  
            public boolean isViewFromObject(View view, Object object){  
                return view == object;  
            }   
            public int getCount(){  
                return mImgIds.length;  
            }  
        });  
	}
	private void initData(){  
        for (int imgId : mImgIds){  
            ImageView imageView = new ImageView(getApplicationContext());  
            imageView.setScaleType(ScaleType.FIT_XY);  
            imageView.setImageResource(imgId);
            mImageViews.add(imageView);  
        }  
    }
	public void http_get_doctor_Login(){
		RequestParams params=new RequestParams();
		params.addBodyParameter("username", AppContext.USER_NAME);
		params.addBodyParameter("password", AppContext.PASS_WORD);
		httpUtils.send(HttpMethod.GET,  AppContext.BASE_URL+AppContext.DOCROT_LOGIN, params, new RequestCallBack<String>() {
			@Override
	        public void onLoading(long total, long current, boolean isUploading) {
	            
	        }			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String doctor_info=responseInfo.result;
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(doctor_info);
					if (jsonObject.optBoolean("success")){
						JSONObject jsonObject2=jsonObject.optJSONObject("data");
						AppContext.REMEMBER_TOKEN=jsonObject2.optString("remember_token");
						Log.e("TAG", AppContext.REMEMBER_TOKEN);
						Doctor doctor = JsonUtil.fromJson(jsonObject2.getString("doctor"), Doctor.class);
						Log.e("TAG", doctor.toString());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e(doctor_info);
				
				Intent intent=new Intent(LoginGuideFrist.this,MainGroupActivity.class);
				startActivity(intent);
			}
			@Override
			public void onFailure(HttpException error, String msg) {			
				
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.btn_login:
			break;
		case R.id.btn_register:
			break;			
		case R.id.btn_tiyanmoshi_click:
			http_get_doctor_Login();
		}		
	}
}
