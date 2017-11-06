package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.api.IUserBiz
import com.example.administrator.mydribbble.biz.http.RetrofitFactory
import com.example.administrator.mydribbble.entity.NullResponse
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.RxHelper
import rx.Subscription

/**
 * Created by Administrator on 2017/9/26.
 */
class UserBiz :BaseBiz(),IUserBiz{
    override fun getToken(oauthCode: String, netSubsriber: NetSubsrciber<Token>): Subscription {
           RetrofitFactory.getInstance()
                   .createWebSiteRetrofit()
                   .getToken(oauthCode = oauthCode)
                   .compose(RxHelper.singleModeThread())
                   .subscribe(netSubsriber)

        return netSubsriber;

    }

    override fun getMyInfo(access_token: String, netSubsriber: NetSubsrciber<User>): Subscription {
           getNetService().getMyInfo(access_token)
                   .compose(RxHelper.singleModeThread())
                   .subscribe(netSubsriber)

           return netSubsriber
    }

    override fun getUserShot(user: String, id: String?, access_token: String, page: Int?, netSubsriber: NetSubsrciber<MutableList<Shot>>): Subscription {
           getNetService().getUserShot(user, id, access_token, page)
               .compose(RxHelper.listModeThread())
               .subscribe(netSubsriber)
       return netSubsriber
    }

    override fun checkIfFollowingUser(id: Long, access_token: String, netSubsriber: NetSubsrciber<NullResponse>): Subscription {
           getNetService().checkIfFollowingUser(id, access_token)
               .compose(RxHelper.singleModeThread())
               .subscribe(netSubsriber)
      return netSubsriber
    }

    override fun followUser(id: Long, access_token: String, netSubsriber: NetSubsrciber<NullResponse>): Subscription {
           getNetService().followUser(id, access_token)
               .compose(RxHelper.singleModeThread())
               .subscribe(netSubsriber)
      return netSubsriber
    }

    override fun unFollowUser(id: Long, access_token: String, netSubsriber: NetSubsrciber<NullResponse>): Subscription {
          getNetService().unfollowUser(id, access_token)
              .compose(RxHelper.singleModeThread())
              .subscribe(netSubsriber)
        return netSubsriber
    }
}