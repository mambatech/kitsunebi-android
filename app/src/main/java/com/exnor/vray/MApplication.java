package com.exnor.vray;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * created by edison 2020/3/18
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        // init umeng sdk
        UMConfigure.init(this, "5e71efe2167eddc4420000e8", "umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        //统计SDK是否支持采集在子进程中打点的自定义事件，默认不支持
        //UMConfigure.setProcessEvent(true);//支持多进程打点
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

    }
}
