package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Like

/**
 * Created by Administrator on 2017/11/10.
 */
interface ILikeView:IBaseView {
  fun getLikesSuccess(likes:MutableList<Like>?,isLoadMore:Boolean)

  fun getLikesFailed(msg:String,isLoadMore: Boolean)
}