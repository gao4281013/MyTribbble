package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Shot
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/11/8.
 */
interface IBucketShotsView:IBaseView {
  /**
   * 获取shot成功
   * @param shot 列表
   * @param isLoadMore 是否是上拉加载
   * */
  fun getShotSuccess(shots:MutableList<Shot>?,isLoadMore:Boolean)

  /**
   * 获取sho失败
   * @param msg 失败原因
   * @param isLoadMore 是否是上拉加载
   * */
  fun getShotFailed(@NotNull msg:String,isLoadMore:Boolean)

  fun removeShotSuccess()

  fun removeShotFailed(msg: String)
}