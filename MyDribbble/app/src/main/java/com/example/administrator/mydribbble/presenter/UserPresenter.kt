package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IUserBiz
import com.example.administrator.mydribbble.biz.impl.UserBiz
import com.example.administrator.mydribbble.entity.NullResponse
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.api.IUserView

/**
 * Created by Administrator on 2017/11/6.
 */
class UserPresenter(val mIUserView: IUserView):BasePresenter() {
   private val mUserBiz:IUserBiz by lazy {
      UserBiz()
   }

  fun getUserShot(user:String,id:String?=null,
              token:String= if (singleData.isLogin())singleData.token!! else Constant.ACCESS_TOKEN,
              page:Int,
      isLoadMore:Boolean){
    val  subscribe = mUserBiz.getUserShot(user,id,token,page,object
      :NetSubsrciber<MutableList<Shot>>(mIUserView){
      override fun onNext(t: MutableList<Shot>?) {
        mIUserView.getShotSuccess(t,isLoadMore)
      }

      override fun onFailed(msg: String) {
        mIUserView.getShotFailed(msg,isLoadMore)
      }

    })
     mSubScription.add(subscribe)
  }

  fun checkIfFollingUser(id:Long){
    val subscribe = mUserBiz.checkIfFollowingUser(id, singleData.token!!,
        object:NetSubsrciber<NullResponse>(mIUserView){
          override fun onFailed(msg: String) {
            mIUserView.notFollowing()
          }

          override fun onNext(t: NullResponse?) {
            mIUserView.following()
          }
        })

     mSubScription.add(subscribe)
  }

  fun folloUser(id: Long){
    val subscribe = mUserBiz.followUser(id, singleData.token!!,object
      :NetSubsrciber<NullResponse>(mIUserView){
      override fun onNext(t: NullResponse?) {
        mIUserView.followUserSuccess()
      }

      override fun onFailed(msg: String) {
        mIUserView.followUserFailed(msg)
      }
    })
  }

  fun unFollowUser(id: Long){
    val subscribe = mUserBiz.unFollowUser(id, singleData.token!!,object
      :NetSubsrciber<NullResponse>(mIUserView){
      override fun onNext(t: NullResponse?) {
        mIUserView.unFollowUserSuccess()
      }

      override fun onFailed(msg: String) {
        mIUserView.unFollowUserFailed(msg)
      }
    })
  }

}