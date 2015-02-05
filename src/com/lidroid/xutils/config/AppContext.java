package com.lidroid.xutils.config;

import android.content.Context;

public class AppContext {
	public static String POST_METHOD="post";
	public static String GET_METHOD="get";
	public static String PUT_METHOD="put";
	public static String DELETE_METHOD="delete";
	public static String USER_NAME="ws1";
	public static String PASS_WORD="123456";
	public static String REMEMBER_TOKEN="";
	public static String PACKAGE_NAME = "com.example.doctor";
	public static String SYS = "android";
	public static String IS_AUTH="";
	public static String NO_AUTHENTICATE="no_authenticate";
	public static String AUTHENTICATED="authenticated";
	public static String FREEZE="freeze";
	public static String AUTHENTICATE_AGAIN="authenticate_again";
	public static Boolean IS_ExcMode=false;
	public static String IMAGE_FILE = "tongxinfile/files/";
	public static String TEST_DOCTOR_NAME="10000";
	public static String TEST_DOCTOR_PASSWORD="123456";
	public static String TEST_WORKMANAGER_TIME="2014-12-25";
	public static String VERSION = "1.12";
	public static String HOST = "servicetest.txzs.org";
	public static String BASE_URL = "http://servicetest.txzs.org/";
	public static String WEIXIN_BASE_URL = "http://webtest.txzs.org/";
	public static String BASE_IMAGE_URL = "http://tongxin-dev-image.oss-cn-beijing.aliyuncs.com/";
	public static String DOCROT_LOGIN="doctor_login.json";
	public static String DOCROT_PATIENTS="trfriends.json";
	public static String version = "1.12";
	public static String sys = "android";
	
	public static void init_url(boolean flag){
		if(flag){
			HOST = "service.txzs.org";
		    BASE_URL ="http://service.txzs.org/";
			BASE_IMAGE_URL = "http://tongxin-dev-image.oss-cn-beijing.aliyuncs.org/";
			WEIXIN_BASE_URL = "http://www.txzs.org/";
		}else{
			HOST = "servicetest.txzs.org";
			BASE_URL = "http://servicetest.txzs.org/";
			WEIXIN_BASE_URL = "http://webtest.txzs.org/";
			BASE_IMAGE_URL = "http://tongxin-dev-image.oss-cn-beijing.aliyuncs.com/";
		}
	}
	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	 
}
