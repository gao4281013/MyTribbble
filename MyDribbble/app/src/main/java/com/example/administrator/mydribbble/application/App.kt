package com.example.administrator.mydribbble.application

import android.app.Application
import android.os.Environment
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.delegates.NotNullSingleValueVar
import com.example.administrator.mydribbble.view.MainActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * Created by Administrator on 2017/9/5 0005.
 */
class App :Application() {
    //将application单例化，可供全局调用context
    companion object {
        var instance:App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        instance = this
        FileDownloader.init(applicationContext)
        Thread{
            Fresco.initialize(this)
        }.start()
        initBugly()
    }

    fun initBugly(){
        Beta.initDelay = 3000
        Beta.largeIconId = R.mipmap.ic_launcher
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.smallIconId = R.drawable.ic_update_black_24dp
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Bugly.init(applicationContext,Constant.BUGLY_ID,true)
    }


}