package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.ILikeBiz
import com.example.administrator.mydribbble.entity.Like
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import rx.Subscription

/**
 * Created by Administrator on 2017/11/10.
 */
class LikeBiz:BaseBiz(),ILikeBiz {
  override fun getMyLikes(access_token: String, page: Int?,
      subsrciber: NetSubsrciber<MutableList<Like>>): Subscription {
        getNetService().getMyLikes(access_token, page)
            .compose(RxHelper.listModeThread())
            .subscribe(subsrciber)
    return subsrciber
  }
}