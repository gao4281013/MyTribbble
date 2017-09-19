package com.example.administrator.mydribbble.tools

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.example.administrator.mydribbble.R
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


fun Fragment.startSpeak(){
    //通过Intent 传递语音识别的模式
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    //语言模式和自由模式的语音识别
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    //提示语音开始
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,resources.getString(R.string.start_speak))
    //开始执行我们的intent,语音识别
    startActivityForResult(intent,Constant.VOICE_CODE)
}
