package com.example.administrator.mydribbble.biz.http

import com.example.administrator.mydribbble.entity.Shot
import org.jetbrains.annotations.NotNull
import retrofit2.http.GET
import retrofit2.http.Query
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



}