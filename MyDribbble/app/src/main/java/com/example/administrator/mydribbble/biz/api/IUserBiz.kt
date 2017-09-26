package com.example.administrator.mydribbble.biz.api

import com.example.administrator.mydribbble.entity.NullResponse
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.tools.NetSubsrciber
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/9/26.
 */
interface IUserBiz {

    /**
     *获取登录token
     **/
    fun getToken(oauthCode:String,netSubsriber:NetSubsrciber<Token>):Subscription

    /**
     * 获取登录的用户信息
     * @param access_token 用户登录后获得的token
     * */
    fun getMyInfo(@NotNull access_token:String,netSubsriber: NetSubsrciber<User>):Subscription

    /**
     * 获取一个用户的shot
     * @param user 用户类型 user是自己  users是其他用户
     * @param id 用户id  是自己则为null
     * @param access_token
     * @param page 页码
     * */
    fun getUserShot(@NotNull user:String,id:String?,@NotNull access_token: String,page:Int?,netSubsriber: NetSubsrciber<MutableList<Shot>>):Subscription


    /**
     * 检查是否已关注这个用户
     * @param access_token
     * @param id 要检查的用户的id
     * */
    fun checkIfFollowingUser(@NotNull id:Long,@NotNull access_token: String,netSubsriber: NetSubsrciber<NullResponse>):Subscription

    /**
     * 关注一个用户
     * @param access_token
     * @param id 要关注用户的id
     * */
    fun followUser(@NotNull id:Long,@NotNull access_token: String,netSubsriber: NetSubsrciber<NullResponse>):Subscription

    /**
     * 取消关注一个用户
     * @param access_token
     * @param id 要取消关注的用户id
     * */
    fun unFollowUser(@NotNull id:Long,@NotNull access_token: String,netSubsriber: NetSubsrciber<NullResponse>):Subscription

}