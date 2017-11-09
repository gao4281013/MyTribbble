package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IMyBucketsBiz
import com.example.administrator.mydribbble.biz.impl.MyBucketShotBiz
import com.example.administrator.mydribbble.entity.Bucket
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.api.IMyBucketsView
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/11/9.
 */
class MyBucketPresenter(val mIMyBucketsView: IMyBucketsView):BasePresenter() {
  private val mMyBucketsBiz:IMyBucketsBiz by lazy {
    MyBucketShotBiz()
  }

  fun getMyBuckets(@NotNull token:String,page:Int?=null){
    mMyBucketsBiz.getMyBuckets(token,page,object :NetSubsrciber<MutableList<Bucket>>
    (mIMyBucketsView){
      override fun onFailed(msg: String) {
         mIMyBucketsView.getBucketsFailed(msg)
      }

      override fun onNext(t: MutableList<Bucket>?) {
        mIMyBucketsView.getBucketsSuccess(t)
      }
    })
  }

  fun createBucket(@NotNull token: String,@NotNull name:String,description:String?){
    mMyBucketsBiz.createBucket(token,name,description,object : NetSubsrciber<Bucket>
    (mIMyBucketsView){
      override fun onNext(t: Bucket?) {
          mIMyBucketsView.createBucketSuccess(t)
      }

      override fun onFailed(msg: String) {
        mIMyBucketsView.createBucketFailed(msg)
      }

      override fun onStart() {
        mIMyBucketsView.showProgressDialog()
        super.onStart()
      }

      override fun onCompleted() {
        super.onCompleted()
        mIMyBucketsView.hideProgressDialog()
      }
    })
  }

  fun deleteBucket(@NotNull token: String,@NotNull id:Long){
    mMyBucketsBiz.deleteBucket(id,token,object : NetSubsrciber<Bucket>(){
      override fun onFailed(msg: String) {
         mIMyBucketsView.deleteBucketFailed(msg)
      }

      override fun onNext(t: Bucket?) {
        mIMyBucketsView.deleteBucketSuccess()
      }

      override fun onStart() {
        mIMyBucketsView.showProgressDialog()
        super.onStart()
      }

      override fun onCompleted() {
        super.onCompleted()
        mIMyBucketsView.hideProgressDialog()
      }
    })
  }

  fun modifyBucket(@NotNull token: String,@NotNull id: Long,@NotNull name: String,description:
  String?,position: Int){
    mMyBucketsBiz.modifyBucket(token,id,name,description,object : NetSubsrciber<Bucket>(){
      override fun onFailed(msg: String) {
           mIMyBucketsView.modifyBucketFailed(msg)
      }

      override fun onNext(t: Bucket?) {
        mIMyBucketsView.modifyBucketSuccess(t,position)
      }

      override fun onStart() {
        mIMyBucketsView.showProgressDialog()
        super.onStart()
      }

      override fun onCompleted() {
        super.onCompleted()
        mIMyBucketsView.hideProgressDialog()
      }
    })
  }

  fun addShot2Bucket(@NotNull id: Long,@NotNull token: String = singleData.token!!,@NotNull
  shotId:Long){
    mMyBucketsBiz.addShot2Bucket(id,token,shotId,object :NetSubsrciber<Shot>(){
      override fun onNext(t: Shot?) {
          mIMyBucketsView.addShotSuccess()
      }

      override fun onFailed(msg: String) {
         mIMyBucketsView.addShotFailed(msg)
      }

      override fun onStart() {
        mIMyBucketsView.showProgressDialog()
        super.onStart()
      }

      override fun onCompleted() {
        mIMyBucketsView.hideProgressDialog()
        super.onCompleted()
      }
    })
  }


}