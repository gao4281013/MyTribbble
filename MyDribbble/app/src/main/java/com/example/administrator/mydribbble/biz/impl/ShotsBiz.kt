package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IShotsBiz
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/9/29 0029.
 */
class ShotsBiz:IShotsBiz,BaseBiz() {

    override fun getShots(@NotNull access_token: String,
                          list: String?,
                          timeframe: String?,
                          sort: String?,
                          page: Int?,
                          subsrciber: NetSubsrciber<MutableList<Shot>>): Subscription {

        getNetService().getShots(access_token,list,timeframe,sort,page)
                .compose(RxHelper.listModeThread())
                .subscribe(subsrciber)

        return subsrciber
    }
}