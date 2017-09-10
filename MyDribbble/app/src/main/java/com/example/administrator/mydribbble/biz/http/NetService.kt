package com.example.administrator.mydribbble.biz.http

import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.tools.Constant
import org.jetbrains.annotations.NotNull
import retrofit2.http.*
import rx.Observable

/**
 * Created by Administrator on 2017/9/6 0006.
 */
interface NetService {
    /**
     * 获取一个shot列表
     *
     * @param access_token token 默认值为公用token
     * @param list  列表类型，比如带GIF的，团队的  animated ，attachments ，debuts ，playoffs，rebounds，teams
     * @param timeframe 时间   week一周，month一个月，year一键，ever无论何时
     * @param sort 排序 comments评论最多的，recent 最近的，views查看最多的
     * @param page 获取哪一页
     *
     * */

    @GET("shots") fun getShots(@NotNull @Query("access_token") access_token:String,
                               @Query("list") list:String?,
                               @Query("timeframe") timeframe:String?,
                               @Query("sort") sort:String?,
                               @Query("page") page:Int):Observable<MutableList<Shot>>


    /**
     *用户登录获取token
     * @param oauthCode  跳转到浏览器用户登录后返回的code
     * */
    @FormUrlEncoded
    @POST("/oauth/token") fun getToken(@Field("client_id") clientid:String = Constant.CLIENT_ID,
                                       @Field("client_secret") clientSecret:String = Constant.CLIENT_SECRET,
                                       @Field("code") oauthCode:String):Observable<Token>

}