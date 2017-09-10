package com.example.administrator.mydribbble.tools

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.example.administrator.mydribbble.application.App

/**
 * Created by Administrator on 2017/9/9.
 */

val View.ctx:Context
     get() = context

fun Activity.showSnackBar(view:View,msg:String,time:Int = Snackbar.LENGTH_SHORT){
     Snackbar.make(view,msg,time).show()
}

fun Any.toast(msg:Int,length:Int = Toast.LENGTH_SHORT){
     Toast.makeText(App.instance,msg,length).show()
}
