package com.example.administrator.mydribbble.tools

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.administrator.mydribbble.application.App

/**
 * Created by Administrator on 2017/9/28 0028.
 * 该类是处理sharePreferences的工具类
 */
object QuickSimpleIO {

    private val mPreferences:SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.instance)
    }

    private val mEditor:SharedPreferences.Editor by lazy {
        mPreferences.edit()
    }

    fun setString(key:String,value:String):Boolean{
        mEditor.putString(key,value)
        return mEditor.commit()
    }

    fun getString(key: String):String?{
        return mPreferences.getString(key,null)
    }

    fun setBoolean(key:String,value:Boolean):Boolean{
        mEditor.putBoolean(key,value)
        return mEditor.commit()
    }

    fun getBoolean(key: String):Boolean?{
        return mPreferences.getBoolean(key,false)
    }

    fun setInt(key:String,value:Int):Boolean{
        mEditor.putInt(key,value)
        return mEditor.commit()
    }

    fun getInt(key: String):Int?{
        return mPreferences.getInt(key,0)
    }

    fun setFloat(key:String,value:Float):Boolean{
        mEditor.putFloat(key,value)
        return mEditor.commit()
    }

    fun getFloat(key: String):Float?{
        return mPreferences.getFloat(key,0f)
    }

    fun setLong(key:String,value:Long):Boolean{
        mEditor.putLong(key,value)
        return mEditor.commit()
    }

    fun getLong(key: String):Long?{
        return mPreferences.getLong(key,0)
    }

    fun clear(){
        mEditor.clear()
    }

    fun remove(key: String):Boolean{
        mEditor.remove(key)
        return mEditor.commit()
    }


}