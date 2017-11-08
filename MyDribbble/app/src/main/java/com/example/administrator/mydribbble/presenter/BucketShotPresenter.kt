package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IShotBucketsBiz
import com.example.administrator.mydribbble.biz.impl.ShotBucketBiz
import com.example.administrator.mydribbble.view.api.IBucketShotsView

/**
 * Created by Administrator on 2017/11/8.
 */
class BucketShotPresenter(val mIBucketShotsView: IBucketShotsView):BasePresenter() {
   val mBucketShotBiz:IShotBucketsBiz by lazy {
       ShotBucketBiz()
   }
}