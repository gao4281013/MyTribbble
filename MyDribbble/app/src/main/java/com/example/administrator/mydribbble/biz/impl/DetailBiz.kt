package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IDetailsBiz
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.LikeShotResponse
import com.example.administrator.mydribbble.tools.NetSubsrciber
import rx.Subscription

/**
 * Created by Administrator on 2017/10/13.
 */
class DetailBiz:IDetailsBiz,BaseBiz() {
  override fun getComments(id: Long, access_token: String, page: Int?,
      subscriber: NetSubsrciber<MutableList<Comment>>): Subscription {

  }

  override fun likeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun checkIfLikeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun unLikeShot(id: Long, access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>): Subscription {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createComment(id: Long, access_token: String, body: String,
      subscriber: NetSubsrciber<Comment>): Subscription {

  }
}