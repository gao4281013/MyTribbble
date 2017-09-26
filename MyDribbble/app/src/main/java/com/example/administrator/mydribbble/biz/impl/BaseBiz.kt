package com.example.administrator.mydribbble.biz.impl

import com.example.administrator.mydribbble.biz.http.NetService
import com.example.administrator.mydribbble.biz.http.RetrofitFactory

/**
 * Created by Administrator on 2017/9/26.
 */
open class BaseBiz {
    fun getNetService():NetService = RetrofitFactory.getInstance().getService()
}