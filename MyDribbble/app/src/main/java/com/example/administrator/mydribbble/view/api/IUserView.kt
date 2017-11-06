package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Shot
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/11/6.
 */
interface IUserView:IBaseView {

  /**
   * 获取shot成功
   * @param shot shot列表
   * @param isLoadMore 是否是上拉加载
   * */

  fun getShotSuccess(shots:MutableList<Shot>?,isLoadMore:Boolean)

  /**
   * 获取shot失败
   * @param msg 失败原因
   * @param isLoadMore 是否是上拉加载
   * */
  fun getShotFailed(@NotNull msg:String,isLoadMore:Boolean)

   /**
    * 关注用户成功
    * */
  fun followUserSuccess()

  /**
   * 关注用户失败
   * */
  fun followUserFailed(msg: String)

  /**
   * 取消关注成功
   * */
  fun unFollowUserSuccess()

  /**
   * 取消关注失败
   * */
  fun unFollowUserFailed(msg: String)

  /**
   * 已关注
   * */
  fun following()

  /**
   * 未关注
   * */
  fun notFollowing()
}