package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Shot
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/9/29 0029.
 */
interface IShotView:IBaseView {
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

}