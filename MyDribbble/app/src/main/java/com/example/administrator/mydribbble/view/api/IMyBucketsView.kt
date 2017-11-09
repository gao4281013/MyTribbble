package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Bucket

/**
 * Created by Administrator on 2017/11/9.
 */
interface IMyBucketsView:IBaseView {

  fun getBucketsSuccess(buckets:MutableList<Bucket>?)

  fun getBucketsFailed(msg:String)

  fun createBucketSuccess(bucket:Bucket?)

  fun createBucketFailed(msg: String)

  fun deleteBucketSuccess()

  fun deleteBucketFailed(msg: String)

  fun modifyBucketSuccess(bucket: Bucket?,position:Int)

  fun modifyBucketFailed(msg: String)

  fun addShotSuccess()

  fun addShotFailed(msg: String)

}