package com.lidroid.xutils.sample;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class CrashShowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash_show);
		//接收异常对象
		Intent intent = getIntent();
		Throwable e = (Throwable) intent.getSerializableExtra("e");
		//构建字符串
		StringBuffer sb = new StringBuffer();
		sb.append("非常抱歉，程序运行过程中出现了一个无法避免的错误。");
//		sb.append("请您及时与开发者取得联系并反馈该问题，此举将有助于我们改善应用体验。");
//		sb.append("您可以尝试重新启动应用来避免该错误。");
//		sb.append("由此给您带来的诸多不便，我们深表歉意，敬请谅解。\n");
//		sb.append("我们的联系方式\n");
//		sb.append("客服电话：010-82864722\n");
//		sb.append("沟通邮箱：GT@tongxinyiliao.com\n");
//		sb.append("交流QQ群：2406492362\n");
		
		//以下部分发布前请注释
//		sb.append("----------------\n");
//		sb.append("异常时间：\n");
//		Time time = new Time();
//		time.setToNow();
//		sb.append(time.toString() + "\n\n");
//		sb.append("异常类型：\n");
//		sb.append(e.getClass().getName() + "\n\n");
//		sb.append("异常信息：\n");
//		sb.append(e.getMessage() + "\n\n");
//		sb.append("异常堆栈：\n" );
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
		//到此
		Button btn_crach_start=(Button) findViewById(R.id.btn_crach_start);
		btn_crach_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CrashShowActivity.this,LoadingFristActivity.class);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		});
		//显示信息
		TextView tvInfo = (TextView) findViewById(R.id.crash_show_tv_info);
		tvInfo.setText(sb.toString());
	}
	
	public void onBtnExit(View view) {
        Intent intent = new Intent(this, LoadingFristActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}

	@Override
	public void onBackPressed() {
		onBtnExit(null);
	}

}
