package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IMyBucketsBiz
import com.example.administrator.mydribbble.entity.Bucket
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import rx.Subscription

/**
 * Created by Administrator on 2017/11/9.
 */
class MyBucketShotBiz:BaseBiz(),IMyBucketsBiz {

  override fun getMyBuckets(access_token: String, page: Int?,
      subsrciber: NetSubsrciber<MutableList<Bucket>>): Subscription {
      getNetService().getMyBuckets(access_token, page)
          .compose(RxHelper.listModeThread())
          .subscribe(subsrciber)
    return subsrciber
  }

  override fun createBucket(access_token: String, name: String, description: String?,
      subsrciber: NetSubsrciber<Bucket>): Subscription {
     getNetService().createBucket(access_token, name, description)
         .compose(RxHelper.singleModeThread())
         .subscribe(subsrciber)
    return subsrciber
  }

  override fun deleteBucket(id: Long, access_token: String,
      subsrciber: NetSubsrciber<Bucket>): Subscription {
      getNetService().deleteBucket(id, access_token)
          .compose(RxHelper.singleModeThread())
          .subscribe(subsrciber)
    return subsrciber
  }

  override fun modifyBucket(access_token: String, id: Long, name: String, description: String?,
      subsrciber: NetSubsrciber<Bucket>): Subscription {
      getNetService().modifyBucket(id, access_token, name, description)
          .compose(RxHelper.singleModeThread())
          .subscribe(subsrciber)
    return subsrciber
  }

  override fun addShot2Bucket(id: Long, access_token: String, shot_id: Long?,
      subsrciber: NetSubsrciber<Shot>): Subscription {
      getNetService().addShot2Bucket(id, access_token, shot_id)
          .compose(RxHelper.singleModeThread())
          .subscribe(subsrciber)
    return subsrciber
  }
}