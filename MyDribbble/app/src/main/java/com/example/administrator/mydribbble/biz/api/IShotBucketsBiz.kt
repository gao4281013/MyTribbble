package com.example.administrator.mydribbble.biz.api

import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/11/8.
 */
interface IShotBucketsBiz {

  /**
   * 获取一个bucket中的列表
   * */
  fun getBucketshots(@NotNull id:Long,
      @NotNull access_token:String,
      page:Int?,
      netSubsrciber: NetSubsrciber<MutableList<Shot>>):Subscription

  /**
   * 从一个bucket中删除一个shot
   * */
  fun removeShotFromBucket(@NotNull access_token: String,
      @NotNull id: Long,
      @NotNull shot_id:Long?,
      netSubsrciber: NetSubsrciber<Shot>):Subscription
}