package com.lidroid.xutils.sample;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.BaseActivity;
import com.lidroid.xutils.bean.Doctor;
import com.lidroid.xutils.config.AppContext;
import com.lidroid.xutils.custom.view.EditTextWithDel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.StringUtils;

public class DoctorLoginActivity extends BaseActivity {
	@SuppressWarnings("unused")
	private EditTextWithDel doctorLoginNameEditText=null;
	@SuppressWarnings("unused")
	private EditTextWithDel doctorLoginPasswordEditText=null;
	@SuppressWarnings("unused")
	private TextView doctorLoginForgetPasswordTextView=null;
	@SuppressWarnings("unused")
	private Button doctorLoginLoginButton=null;
	@SuppressWarnings("unused")
	private TextView text_doctor_login_back=null;
	@SuppressWarnings("unused")
	private SharedPreferences sp_login=null;
	@SuppressWarnings("unused")
	private Editor edt=null;
	private String user_name="";
	private String password="";
	private Boolean logingFlg=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_login);
		hideTitleView();
		initView();
		init_shp();
		init_user();
		if(logingFlg){
			login_in();
		}
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent=new Intent(DoctorLoginActivity.this,MainGroupActivity.class);
				startActivity(intent);
				break;
			case 1:
				
				break;
			default:
				break;
			}
		};
	};
	public void initView(){
		doctorLoginNameEditText=(EditTextWithDel) findViewById(R.id.doctorLoginNameEditText);
		doctorLoginPasswordEditText=(EditTextWithDel) findViewById(R.id.doctorLoginPasswordEditText);
		doctorLoginForgetPasswordTextView=(TextView) findViewById(R.id.doctorLoginForgetPasswordTextView);
		doctorLoginLoginButton=(Button) findViewById(R.id.doctorLoginLoginButton);
		doctorLoginLoginButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				login_in();
			}
		});
		text_doctor_login_back=(TextView) findViewById(R.id.text_doctor_login_back);
	}
	public void init_shp(){
		sp_login=getSharedPreferences("doctor_login", Context.MODE_PRIVATE);
		edt=sp_login.edit();
	}
	public void init_user(){
		doctorLoginNameEditText.setText(sp_login.getString("user_name", ""));
		doctorLoginPasswordEditText.setText(sp_login.getString("password", ""));
		logingFlg=sp_login.getBoolean("flg", false);
	
	}
	public void http_get_doctor_Login(final String username, final String password){
		RequestParams params=new RequestParams();
		params.addBodyParameter("username", username);
		params.addBodyParameter("password", password);
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
						edt.putString("user_name", username);
						edt.putString("password", password);
						edt.putBoolean("flg",true);
						edt.commit();
						Log.e("TAG", doctor.toString());
						Message msg=new Message();
						msg.what=0;
						handler.sendMessage(msg);
					}else{
						showToast(jsonObject.optString("data"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e(doctor_info);
			}
			@Override
			public void onFailure(HttpException error, String msg) {			
				
			}
		});
	}
	
	public void login_in(){
		user_name=doctorLoginNameEditText.getText().toString().trim();
		password=doctorLoginPasswordEditText.getText().toString().trim();
		if (StringUtils.isBlank(user_name)){
			showToast("请输入用户名");
			return ;
		}
		if (StringUtils.isBlank(password)){
			
			showToast("请输入用户密码");
			return ;
		}
		http_get_doctor_Login(user_name, password);
	}

}
