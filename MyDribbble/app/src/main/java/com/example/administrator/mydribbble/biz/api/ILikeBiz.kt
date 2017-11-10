package com.example.administrator.mydribbble.biz.api

import com.example.administrator.mydribbble.entity.Like
import com.example.administrator.mydribbble.tools.NetSubsrciber
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/11/10.
 */
interface ILikeBiz {
 /**
  * 获取当前用户的like列表
  * */
  fun getMyLikes(@NotNull access_token:String,
     page:Int?,
     subsrciber: NetSubsrciber<MutableList<Like>>):Subscription
}