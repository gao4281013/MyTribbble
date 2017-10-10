package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IShotsBiz
import com.example.administrator.mydribbble.biz.impl.ShotsBiz
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.view.api.IShotView

/**
 * Created by Administrator on 2017/9/29 0029.
 */
class ShotPresenter(val mShotsView:IShotView):BasePresenter() {
    private val mShotsBiz:IShotsBiz by lazy {
        ShotsBiz()
    }

    fun getShots(access_token:String = Constant.ACCESS_TOKEN,list:String?,timeframe:String?,sort:String?,page:Int?,isLoadMore:Boolean){
        val subsrciber = mShotsBiz.getShots(access_token,list,timeframe,sort,page,object : NetSubsrciber<MutableList<Shot>>(mShotsView){
            override fun onNext(t: MutableList<Shot>?) {
                mShotsView.getShotSuccess(t,isLoadMore)
            }

            override fun onFailed(msg: String) {
                mShotsView.getShotFailed(msg,isLoadMore)
            }
        }
        )

        mSubScription.add(subsrciber)
    }





}