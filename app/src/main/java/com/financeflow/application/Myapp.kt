package com.financeflow.application


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.financeflow.analytics.getAnalyticsLogger
import com.financeflow.util.PrefManager
import com.mixpanel.android.mpmetrics.MixpanelAPI


class Myapp : Application(), LifeCycleDelegate {

    lateinit var mypref: PrefManager

    lateinit var appLifecycleHandler: AppLifecycleHandler
    lateinit var  mMixpanel: MixpanelAPI

    companion object {
        var BASE_URL: String = "http://54.91.6.130:8080/api/v1/"
        var BASE_URL1: String = "https://api.stripe.com/v1/"

        lateinit var instance: Myapp
        // private set
    }



    override fun onCreate() {
        super.onCreate()
        instance = this
        mypref = PrefManager(this)
        getAnalyticsLogger().init(this@Myapp)
        appLifecycleHandler = AppLifecycleHandler(this)
        registerLifeCycleHandler(appLifecycleHandler)
        mMixpanel = MixpanelAPI.getInstance(this, "e44ab760a6c9d84f321e1ea343651aeb");




    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onAppBackgrounded() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onAppForegrounded() {

    }


    fun registerLifeCycleHandler(lifecycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifecycleHandler);
        registerComponentCallbacks(lifecycleHandler);
    }


}