package com.example.administrator.mydribbble.tools

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Administrator on 2017/9/6 0006.
 */
object Utils {
    /**
     * 判断网络可不可用
     *
     * @return true为可用
     * */
    fun isNetworkAvailable(context: Context):Boolean{
        val cm:ConnectivityManager?= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val info = cm?.activeNetworkInfo
        if (info != null){
            return info.isAvailable && info.isConnected
        }
        return false
    }


}