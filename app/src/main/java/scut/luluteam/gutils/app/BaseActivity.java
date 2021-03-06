package scut.luluteam.gutils.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by guan on 5/26/17.
 */

public class BaseActivity extends AppCompatActivity {


    /**
     * 自定义Activity样式
     */
    private CustomBuilder customBuilder = new CustomBuilder();

    /**
     * 监听网络状态
     */
    protected static int networkState;
    //protected NetworkStateReceiver networkStateReceiver;


    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册监听网络的广播
//        networkStateReceiver=new NetworkStateReceiver();
//        IntentFilter networkFilter=new IntentFilter();
//        networkFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkStateReceiver,networkFilter);
//        Log.e(TAG,"开始注册");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销广播
        //unregisterReceiver(networkStateReceiver);
    }

    private void init() {
        //getCustomBuilder().build();

        //networkState= NetUtil.getNetworkState(mContext);
        //NetworkStateReceiver.setNetworkStateListener(this);

    }


    //region 定制Activity风格

    /**
     * 获取定制Activity的Builder
     *
     * @return
     */
    protected CustomBuilder getCustomBuilder() {
        return customBuilder;
    }

    /**
     * 根据CustomBuilder中的设置项，开始设置Activity
     */
    private void customActivity() {
        if (customBuilder.noTitle) {
            //设置取消标题栏。但AppCompatActivity对此无用
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //采用这种方式取消标题栏
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }
        if (customBuilder.isSetStatusBar) {
            /**
             * 开始设置：[沉浸状态栏]
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
        if (!customBuilder.isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        /**
         * 对于Android 4.4以上，状态栏设置为全透明
         */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        if (customBuilder.statusBarColor_Id != -1) {
            //取消状态栏透明
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(mContext, customBuilder.statusBarColor_Id));
            }
        }


    }


    //======================================================================

    /**
     * 内部类：用于标识Activity的设置项
     */
    public final class CustomBuilder {
        /**
         * 是否沉浸状态栏:
         **/
        private boolean isSetStatusBar = false;
        /**
         * 是否允许全屏
         **/
        private boolean noTitle = true;
        /**
         * 是否允许旋转屏幕
         **/
        private boolean isAllowScreenRoate = false;

        /**
         * 设置状态栏颜色:对于Android 5.0有效
         */
        private int statusBarColor_Id = -1;

        /**
         * [是否允许全屏]
         *
         * @param noTitle
         */
        public CustomBuilder setNoTitle(boolean noTitle) {
            this.noTitle = noTitle;
            return this;
        }

        /**
         * [是否设置沉浸状态栏]
         *
         * @param isSetStatusBar
         */
        public CustomBuilder setSteepStatusBar(boolean isSetStatusBar) {
            this.isSetStatusBar = isSetStatusBar;
            return this;
        }

        /**
         * [是否允许屏幕旋转]
         *
         * @param isAllowScreenRoate
         */
        public CustomBuilder allowScreenRoate(boolean isAllowScreenRoate) {
            this.isAllowScreenRoate = isAllowScreenRoate;
            return this;
        }

        public CustomBuilder setStatusBarColor_Id(int statusBarColor_Id) {
            this.statusBarColor_Id = statusBarColor_Id;
            return this;
        }


        public void build() {
            customActivity();
        }


    }

    //endregion

}
