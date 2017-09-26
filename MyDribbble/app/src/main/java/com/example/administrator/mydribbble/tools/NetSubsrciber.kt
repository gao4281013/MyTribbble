package com.example.administrator.mydribbble.tools

import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.view.api.IBaseView
import rx.Subscriber

/**
 * Created by Administrator on 2017/9/26.
 */
abstract class NetSubsrciber<T>(val baseView:IBaseView?=null):Subscriber<T>() {
    override fun onStart() {
        super.onStart()
        if (!Utils.isNetworkAvailable(App.instance)){
            baseView?.hideProgress()
            onFailed(App.instance.resources.getString(R.string.net_disable))
            unsubscribe()
        }
    }

    override fun onCompleted() {
        baseView?.hideProgress()
    }

    override fun onError(p0: Throwable?) {
        baseView?.hideProgress()
        p0?.printStackTrace()
        onFailed(p0?.message.toString())
    }

    abstract fun onFailed(msg:String)
}