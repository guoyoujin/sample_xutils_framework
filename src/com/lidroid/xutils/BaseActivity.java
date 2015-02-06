package com.lidroid.xutils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.custom.view.ToastView;
import com.lidroid.xutils.sample.BitmapHelp;
import com.lidroid.xutils.sample.R;
import com.lidroid.xutils.sample.fragment.BitmapFragment;

/**
* 继承于Activity用于以后方便管理
* 
* @author coder
* 
*/
public class BaseActivity extends Activity {
        private TextView tv_title;
        private TextView btn_left, btn_right;
        private LinearLayout linout_title_bar;
        private LinearLayout ly_content;
        public ToastView toast;
        // 内容区域的布局
        private View contentView;
        public static HttpUtils httpUtils=null;
        private Context context;
        public BitmapUtils bitmapUtils;
        public BitmapDisplayConfig bigPicDisplayConfig;
        public BitmapLoadCallBack<ImageView> bitmapLoadCallBack;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                //requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.common_title);
                tv_title = (TextView) findViewById(R.id.tv_title);
                btn_left = (TextView) findViewById(R.id.btn_left);
                btn_right = (TextView)findViewById(R.id.btn_right);
                linout_title_bar=(LinearLayout) findViewById(R.id.linout_title_bar);
                ly_content = (LinearLayout) findViewById(R.id.ly_content);
                if(context==null){
                	context=BaseActivity.this;
                }
                if(httpUtils==null){
                	httpUtils=new HttpUtils();
                }
                
                bitmap_iamge();
        }
        
        public void showToast(String text){
            	toast=new ToastView(getApplicationContext(), text);
            	toast.setDuration(1000);
            	toast.show();
        }

        /***
         * 设置内容区域
         * 
         * @param resId
         *            资源文件ID
         */
        @SuppressWarnings("deprecation")
		@SuppressLint({ "InlinedApi", "NewApi" })
		public void setContentLayout(int resId) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentView = inflater.inflate(resId, null);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT);
                contentView.setLayoutParams(layoutParams);
                contentView.setBackgroundDrawable(null);
                if (null != ly_content) {
                        ly_content.addView(contentView);
                }

        }

        /***
         * 设置内容区域
         * 
         * @param view
         *            View对象
         */
        public void setContentLayout(View view) {
                if (null != ly_content) {
                        ly_content.addView(view);
                }
        }

        /**
         * 得到内容的View
         * 
         * @return
         */
        public View getLyContentView() {

                return contentView;
        }

        /**
         * 得到左边的按钮
         * 
         * @return
         */
        public TextView getbtn_left() {
                return btn_left;
        }

        /**
         * 得到右边的按钮
         * 
         * @return
         */
        public TextView getbtn_right() {
                return btn_right;
        }

        /**
         * 设置标题
         * 
         * @param title
         */
        public void setTitle(String title) {

                if (null != tv_title) {
                        tv_title.setText(title);
                }

        }

        /**
         * 设置标题
         * 
         * @param resId
         */
        public void setTitle(int resId) {
                tv_title.setText(getString(resId));
        }

        /**
         * 设置左边按钮的图片资源
         * 
         * @param resId
         */
        public void setbtn_leftRes(int resId) {
                if (null != btn_left) {
                        btn_left.setBackgroundResource(resId);
                }

        }

        /**
         * 设置左边按钮的图片资源
         * 
         * @param bm
         */
        @SuppressWarnings("deprecation")
		public void setbtn_leftRes(Drawable drawable) {
                if (null != btn_left) {
                        btn_left.setBackgroundDrawable(drawable);
                }

        }

        /**
         * 设置右边按钮的图片资源
         * 
         * @param resId
         */
        public void setbtn_rightRes(int resId) {
                if (null != btn_right) {
                        btn_right.setBackgroundResource(resId);
                }
        }

        /**
         * 设置右边按钮的图片资源
         * 
         * @param drawable
         */
        @SuppressWarnings("deprecation")
		public void setbtn_rightRes(Drawable drawable) {
                if (null != btn_right) {
                        btn_right.setBackgroundDrawable(drawable);
                }
        }

        /**
         * 隐藏上方的标题栏
         */
        public void hideTitleView() {

                if (null != linout_title_bar) {
                	linout_title_bar.setVisibility(View.GONE);
                }
        }
        /**
         * 显示上方的标题栏
         */
        public void showTitleView() {

                if (null != linout_title_bar) {
                	linout_title_bar.setVisibility(View.VISIBLE);
                }
        }

        /**
         * 隐藏左边的按钮
         */
        public void hidebtn_left() {

                if (null != btn_left) {
                        btn_left.setVisibility(View.GONE);
                }

        }

        /***
         * 隐藏右边的按钮
         */
        public void hidebtn_right() {
                if (null != btn_right) {
                        btn_right.setVisibility(View.GONE);
                }

        }

        public BaseActivity() {

        }

		@SuppressWarnings("unchecked")
		public static <T extends View> T getAdapterView(View convertView, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = convertView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
		
		/***
		 * 图片缓存配置
		 * 可以设置默认显示的图片
		 * 是否显示原图
		 * 
		 */
        public void bitmap_iamge(){
        	 bitmapUtils = BitmapFragment.bitmapUtils;
             if (bitmapUtils == null) {
                 bitmapUtils = BitmapHelp.getBitmapUtils(this.getApplicationContext());
             }
             bigPicDisplayConfig = new BitmapDisplayConfig();
             //bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易OOM。
             bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
             bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(this)); 
             bigPicDisplayConfig.setLoadFailedDrawable(convertBitmap2Drawable(getBitmapFromResources(this, R.drawable.doctor_photos)));
             bitmapLoadCallBack = new DefaultBitmapLoadCallBack<ImageView>() {
                 @SuppressLint("ShowToast")
				@Override
                 public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                     super.onLoadStarted(container, uri, config);
                     //Toast.makeText(getApplicationContext(), uri, 300).show();
                 }

                 @SuppressLint("ShowToast")
				@Override
                 public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                     super.onLoadCompleted(container, uri, bitmap, config, from);
                    // Toast.makeText(getApplicationContext(), bitmap.getWidth() + "*" + bitmap.getHeight(), 300).show();
                 }
             };
        }
        
        public static Bitmap getBitmapFromResources(Activity act, int resId) {
        	Resources res = act.getResources();
        	return BitmapFactory.decodeResource(res, resId);
        }
        @SuppressWarnings("deprecation")
		public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
        	BitmapDrawable bd = new BitmapDrawable(bitmap);
        	// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        	return bd;
        }
}
