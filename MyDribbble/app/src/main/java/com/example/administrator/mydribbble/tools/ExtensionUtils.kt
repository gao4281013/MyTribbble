package com.example.administrator.mydribbble.tools

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import org.jetbrains.anko.find
import org.jetbrains.annotations.NotNull

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

fun Any.toast(msg:String,length:Int = Toast.LENGTH_SHORT){
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

fun Any.log(msg: String){
    Log.d(this.javaClass.simpleName,msg)
}


inline fun Context.hasNavigationBar(block:() -> Unit){
    //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey){
        block()
    }
}

/**
 * 显示错误图片
 * */
fun Fragment.showErrorImg(@NotNull viewGroup: ViewGroup,@NotNull msgResId:Int = R.string.no_shot,imgResID: Int = R.mipmap.img_empty_shots){
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 显示错误图片
 * */
fun Fragment.showErrorImg(@NotNull viewGroup: ViewGroup,@NotNull msgResId:String,imgResID: Int = R.mipmap.img_empty_shots){
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 显示错误图片
 * */
fun Activity.showErrorImg(@NotNull viewGroup: ViewGroup,@NotNull msgResId:String,imgResID: Int = R.mipmap.img_empty_shots){
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 隐藏错误图片
 * */
fun Fragment.hideErrorImg(@NotNull viewGroup: ViewGroup){
    viewGroup.visibility = View.GONE
}

/**
 * 隐藏错误图片
 * */
fun Activity.hideErrorImg(@NotNull viewGroup: ViewGroup){
    viewGroup.visibility = View.GONE
}
