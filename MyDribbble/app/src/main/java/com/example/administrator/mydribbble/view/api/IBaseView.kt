package com.example.administrator.mydribbble.view.api

/**
 * Created by Administrator on 2017/9/26.
 */
interface IBaseView {
    fun showProgress(){}

    fun hideProgress(){}

    fun showProgressDialog(msg:String? = null){}

    fun hideProgressDialog(){}
}