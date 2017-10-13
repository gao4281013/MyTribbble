package com.example.administrator.mydribbble.biz.api

import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.LikeShotResponse
import com.example.administrator.mydribbble.tools.NetSubsrciber
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by Administrator on 2017/10/13.
 */
interface IDetailsBiz {
  /**
   * 获取一个shot下的评论列表
   * @param id 这条shot的ID
   * @param access_token token 默认值为公用token
   * @param page 获取哪一页 默认为空，评论别表应为dribbblep评论数量偏少的原因，所以不做上啦加载
   *
   * return MutableList<Comment>
   * */

  fun getComments(@NotNull id:Long,
      @NotNull access_token:String,
      page:Int?,
      subscriber:NetSubsrciber<MutableList<Comment>>):Subscription

   /**
    * 喜欢一个shot
    * */
  fun likeShot(@NotNull id:Long,
       @NotNull access_token: String,
       subscriber: NetSubsrciber<LikeShotResponse>):Subscription

  /**
   * 检查这个shot是否已经被喜欢
   * */
  fun checkIfLikeShot(@NotNull id:Long,
      @NotNull access_token: String,
      subscriber: NetSubsrciber<LikeShotResponse>):Subscription
 /**
  * 删除一个like的喜欢
  * */
  fun unLikeShot(@NotNull id:Long,
     @NotNull access_token: String,
     subscriber: NetSubsrciber<LikeShotResponse>):Subscription

  /**
   * 创建一条评论
   * */
  fun createComment(@NotNull id:Long,
      @NotNull access_token: String,
      @NotNull body:String,
      subscriber: NetSubsrciber<Comment>):Subscription

}