package com.lidroid.xutils.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance; //当前异常处理器-单例模式
	@SuppressWarnings("unused")
	private UncaughtExceptionHandler defaultHandler; //系统默认未捕获异常处理器
	private Context context; //应用上下文

	/**
	 * 隐藏构造器
	 */
	private CrashHandler() {}
	
	/**
	 * 获取异常处理器单例
	 */
	public static synchronized CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}
	
	/**
	 * 激活处理器
	 * @param context
	 */
	public void active(Context context) {
		this.context = context; 
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	/**
	 * 未被捕获的异常，会调用该方法处理
	 */
	@Override
	public void uncaughtException(Thread thread, final Throwable e) {
		//系统默认处理异常
		//defaultHandler.uncaughtException(thread, e);
		
		//sdcard保存日志文件
		collectDeviceInfo(context);
		saveCrashInfo2File(e);

		//启动ErrorShowActivity
        Intent intent = new Intent(context, CrashShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("e", e);
        intent.putExtras(bundle);
        context.startActivity(intent);

		//退出程序
		//android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}

    /** 
     * 收集设备参数信息 
     * @param ctx 
     */
	 //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();  
	
    public void collectDeviceInfo(Context ctx) {  
        try {  
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null" : pi.versionName;  
                String versionCode = pi.versionCode + "";  
                infos.put("versionName", versionName);  
                infos.put("versionCode", versionCode);  
            }  
        } catch (NameNotFoundException e) {  
        }  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
            } catch (Exception e) {   
            }  
        }  
    }
	
	/** 
     * 保存错误信息到文件中
     */
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  

    private String saveCrashInfo2File(Throwable e) {  
    	StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        e.printStackTrace(printWriter);  
        Throwable cause = e.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date());  
            String fileName = "crash-" + time + "-" + timestamp + ".log";  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                String path = "/sdcard/crash/";  
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(path + fileName);  
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
            return fileName;  
        } catch (Exception ex) {
        }  
        return null;
    }

}
