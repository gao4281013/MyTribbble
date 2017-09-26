package com.example.administrator.mydribbble.presenter

import rx.subscriptions.CompositeSubscription

/**
 * Created by Administrator on 2017/9/26.
 */
abstract class BasePresenter {

    val mSubScription:CompositeSubscription by lazy {
        CompositeSubscription()
    }

    open fun unSubsrciber(){
        if (mSubScription.hasSubscriptions()){
            mSubScription.unsubscribe()
        }
    }


}