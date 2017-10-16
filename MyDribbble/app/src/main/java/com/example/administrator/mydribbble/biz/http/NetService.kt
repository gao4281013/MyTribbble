package com.example.administrator.mydribbble.biz.http

import com.example.administrator.mydribbble.entity.*
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
                               @Query("page") page:Int?):Observable<MutableList<Shot>>


    /**
     *用户登录获取token
     * @param oauthCode  跳转到浏览器用户登录后返回的code
     * */
    @FormUrlEncoded
    @POST("/oauth/token") fun getToken(@Field("client_id") clientid:String = Constant.CLIENT_ID,
                                       @Field("client_secret") clientSecret:String = Constant.CLIENT_SECRET,
                                       @Field("code") oauthCode:String):Observable<Token>

    /**
     * 获取登录的用户的个人信息
     * @param access_token 登录后获得的用户的token
     * */
    @FormUrlEncoded
    @POST("/oauth/token") fun getMyInfo(@NotNull @Query("access_token") access_token: String):Observable<User>


    /**
     * 创建一条评论
     *
     * @param access_token
     * @param body 评论内容
     * @param id shot的ID
     * */
    @FormUrlEncoded
    @POST("shots/{id}/comments") fun  createComment(@NotNull @Path("id")id:Long,
        @NotNull @Field("access_token") access_token: String,
        @NotNull @Field("body") body:String):Observable<Comment>

    /**
     * 获取一个shot下的评论列表
     *
     * @param id 这条shot的ID
     * @param access_token token 默认值为公用token
     * @param page 获取哪一页 默认为空，评论列表应为dribbble评论偏少的缘故，不做上拉加载
     *
     * return MutableList<Comment>
     * */
    @GET("shots/{id}/comments") fun getComments(@NotNull @Path("id") id: Long,
                                                @NotNull @Query("access_token") access_token: String,
                                                @Query("page") page: Int?,
                                                @Query("per_page") per_page:Int?=100):Observable<MutableList<Comment>>
    /**
     * 喜欢一个shot
     * @param access_token
     * @param id shot的id
     * */
    @FormUrlEncoded
    @POST("shots/{id}/like") fun likeShot(@NotNull @Path("id") id: Long,
                                          @NotNull @Field("access_token") access_token: String):Observable<LikeShotResponse>

    /**
     * 获取这个shot是否被喜欢
     * @param access_token
     * @param id shot的id
     * */
    @GET("shots/{id}/like") fun checkIfLikeShot(@NotNull @Path("id") id: Long,
                                                @NotNull @Query("access_token") access_token: String):Observable<LikeShotResponse>

    /**
     * 删除这个like
     * */
    @DELETE("shot/{id}/like") fun unLikeShot(@NotNull @Path("id") id: Long,
                                             @NotNull @Query("access_token") access_token: String):Observable<LikeShotResponse>


}