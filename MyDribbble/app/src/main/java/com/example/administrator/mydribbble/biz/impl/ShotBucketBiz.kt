package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IShotBucketsBiz
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import rx.Subscription

/**
 * Created by Administrator on 2017/11/8.
 */
class ShotBucketBiz : IShotBucketsBiz, BaseBiz() {
  override fun getBucketshots(id: Long, access_token: String, page: Int?,
      netSubsrciber: NetSubsrciber<MutableList<Shot>>): Subscription {
    getNetService().getBucketShots(id, access_token, page)
        .compose(RxHelper.listModeThread())
        .subscribe(netSubsrciber)

    return netSubsrciber
  }

  override fun removeShotFromBucket(access_token: String, id: Long, shot_id: Long?,
      netSubsrciber: NetSubsrciber<Shot>): Subscription {
    getNetService().removeShotFromBucket(id, access_token, shot_id)
        .compose(RxHelper.singleModeThread())
        .subscribe(netSubsrciber)
    return netSubsrciber
  }
}