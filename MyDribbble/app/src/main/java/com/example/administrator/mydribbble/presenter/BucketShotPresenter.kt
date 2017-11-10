package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IShotBucketsBiz
import com.example.administrator.mydribbble.biz.impl.ShotBucketBiz
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.api.IBucketShotsView
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/11/8.
 */
class BucketShotPresenter(val mIBucketShotsView: IBucketShotsView) : BasePresenter() {
  val mBucketShotBiz: IShotBucketsBiz by lazy {
    ShotBucketBiz()
  }

  fun getBucketShots(id: Long, access_token: String = singleData.token!!, page: Int?,
      isLoadMore: Boolean) {
    val subscribe = mBucketShotBiz.getBucketshots(id, access_token, page, object :
        NetSubsrciber<MutableList<Shot>>(mIBucketShotsView) {
      override fun onNext(t: MutableList<Shot>?) {
        mIBucketShotsView.getShotSuccess(t, isLoadMore)
      }

      override fun onFailed(msg: String) {
        mIBucketShotsView.getShotFailed(msg, isLoadMore)
      }
    })
    mSubScription.add(subscribe)
  }

  fun removeShotFromBuckets(@NotNull access_token: String = singleData.token!!, @NotNull id: Long,
      @NotNull shot_id: Long?) {

    val subscribe = mBucketShotBiz.removeShotFromBucket(access_token, id, shot_id, object
      : NetSubsrciber<Shot>(mIBucketShotsView) {
      override fun onNext(t: Shot?) {
          mIBucketShotsView.removeShotSuccess()
      }

      override fun onFailed(msg: String) {
         mIBucketShotsView.removeShotFailed(msg)
      }
    })
    mSubScription.add(subscribe)
  }
}