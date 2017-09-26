package com.example.administrator.mydribbble.view.api

import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User

/**
 * Created by Administrator on 2017/9/26.
 */
interface IMainView:IBaseView {
    fun  getTokenSuccess(token:Token?)

    fun getTokenFailed(msg:String)

    fun getUserSuccess(user: User?)

}