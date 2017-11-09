package com.example.administrator.mydribbble.biz.api

import com.example.administrator.mydribbble.entity.Bucket
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/11/9.
 */
interface IMyBucketsBiz {

  /**
   * 获取当前用户的bucket列表
   * */
  fun getMyBuckets(@NotNull access_token:String,page:Int?,subsrciber:
  NetSubsrciber<MutableList<Bucket>>):Subscription

  /**
   * 创建一个bucket
   * */
  fun createBucket(@NotNull access_token: String,
      @NotNull name:String,
      description:String?,
      subsrciber: NetSubsrciber<Bucket>):Subscription

  /**
   * 删除一个bucket
   * */
  fun deleteBucket( @NotNull id: Long,
      @NotNull access_token: String,
      subsrciber: NetSubsrciber<Bucket>):Subscription

  /**
   * 修改一个bucket
   * */
  fun modifyBucket(@NotNull access_token: String,
      @NotNull id:Long,
      @NotNull name: String,
      description: String?,
      subsrciber: NetSubsrciber<Bucket>):Subscription

  /**
   * 添加一个shot到一个bucket
   * */
  fun addShot2Bucket(@NotNull id: Long,
      @NotNull access_token: String,
      @NotNull shot_id:Long?,
      subsrciber: NetSubsrciber<Shot>):Subscription

}