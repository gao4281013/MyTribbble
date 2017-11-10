package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.impl.LikeBiz
import com.example.administrator.mydribbble.entity.Like
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.view.api.ILikeView

/**
 * Created by Administrator on 2017/11/10.
 */
class LikePresenter(val mLikeView:ILikeView):BasePresenter(){
  private val mLikeBiz:LikeBiz by lazy {
     LikeBiz()
  }


   fun getMyLikes(access_token: String, page: Int?, isLoadMore:Boolean){
        val subscribe = mLikeBiz.getMyLikes(access_token
            , page,
            object :NetSubsrciber<MutableList<Like>>(mLikeView){
              override fun onNext(t: MutableList<Like>?) {
                  mLikeView.getLikesSuccess(t,isLoadMore)
              }

              override fun onFailed(msg: String) {
                mLikeView.getLikesFailed(msg,isLoadMore)
              }
            })

     mSubScription.add(subscribe)
  }
}