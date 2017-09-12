package com.example.administrator.mydribbble.view.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.administrator.mydribbble.view.dialog.DialogManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class BaseActivity : AppCompatActivity() {
    var screenWidth:Int = 0
    var screenHeight:Int = 0
    val mDialogManager:DialogManager by lazy {
        DialogManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getScreenPixel(){
        val metrics = DisplayMetrics()
        val manager = windowManager
        manager.defaultDisplay.getMetrics(metrics)
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
