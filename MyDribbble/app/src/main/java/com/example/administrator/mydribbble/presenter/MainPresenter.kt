package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.biz.api.IUserBiz
import com.example.administrator.mydribbble.biz.impl.UserBiz
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.log
import com.example.administrator.mydribbble.view.api.IMainView
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/9/26.
 */
class MainPresenter(val mIMainView: IMainView):BasePresenter() {

     private val mUserBiz:IUserBiz by lazy {
          UserBiz()
     }


    fun getToken(oauthCode:String){
        val subscriber = mUserBiz.getToken(oauthCode,object :NetSubsrciber<Token>(mIMainView){
            override fun onNext(p0: Token?) {
                mIMainView.getTokenSuccess(p0)
            }

            override fun onFailed(msg: String) {
                mIMainView.getTokenFailed(msg)
            }
        })
        mSubScription.add(subscriber)
    }

    fun getMyInfo(@NotNull access_token:String){
        val subsrciber = mUserBiz.getMyInfo(access_token,object :NetSubsrciber<User>(){
            override fun onFailed(msg: String) {
                log(msg)
            }

            override fun onNext(p0: User?) {
                mIMainView.getUserSuccess(p0)
            }
        })
        mSubScription.add(subsrciber)
    }
}