package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IDetailsBiz
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.LikeShotResponse
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import rx.Subscription

/**
 * Created by Administrator on 2017/10/13.
 */
class DetailBiz:IDetailsBiz,BaseBiz() {
  override fun getComments(id: Long, access_token: String, page: Int?,
      subscriber: NetSubsrciber<MutableList<Comment>>): Subscription {
     getNetService().getComments(id,access_token,page)
             .compose(RxHelper.listModeThread())
             .subscribe(subscriber)
      return subscriber
  }

  override fun likeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
      getNetService().likeShot(id, access_token)
              .compose(RxHelper.singleModeThread())
              .subscribe(subscriber)
      return subscriber
  }

  override fun checkIfLikeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
      getNetService().likeShot(id, access_token)
              .compose(RxHelper.singleModeThread())
      return subscriber
  }

  override fun unLikeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
      getNetService().unLikeShot(id, access_token)
              .compose(RxHelper.singleModeThread())
              .subscribe(subscriber)
      return subscriber
  }

  override fun createComment(id: Long, access_token: String, body: String,
      subscriber: NetSubsrciber<Comment>): Subscription {
      getNetService().createComment(id, access_token, body)
              .compose(RxHelper.singleModeThread())
              .subscribe(subscriber)

      return subscriber

  }
}