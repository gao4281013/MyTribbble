package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.DownloadUtil
import com.example.administrator.mydribbble.tools.ImageLoad
import kotlinx.android.synthetic.main.activity_image_full.*
import java.io.File

class ImageFullActivity : AppCompatActivity() {
  companion object {
    val KEY_URL_NORMAL:String ="key_url_normal"
    val KEY_URL_LOW:String = "KEY_URL_LOW"
    val KEY_TITLE:String = "key_title"
  }

  private var mUrlNormal:String? = null
  private var mUrlNow:String? = null
  private var mTitle:String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_full)
    mUrlNormal = intent.getStringExtra(KEY_URL_NORMAL)
    mUrlNow = intent.getStringExtra(KEY_URL_LOW)
    mTitle = intent.getStringExtra(KEY_TITLE)
    initView()
  }

  private fun initView() {
    image_full_tool_bar.inflateMenu(R.menu.image_full_menu)
    if (mUrlNormal!=null){
       ImageLoad.frescoLoadZoom(mImage,image_progress_bar,mUrlNormal.toString(),mUrlNow,true)
    }
  }

  override fun onStart() {
    super.onStart()
    bindEvent()
  }

  private fun bindEvent() {
    image_full_tool_bar.setNavigationOnClickListener {
      finish()
    }

    image_full_tool_bar.setOnMenuItemClickListener { item ->
      when(item.itemId){
        R.id.mDownload ->{
          val urls = mUrlNormal?.split(".")
          DownloadUtil.DownloadImg(mUrlNormal.toString()
              ,"${Constant.IMAGE_DOENLOAD_PATH}${File.separator}$mTitle.${urls!![urls.size-1]}")

        }
        }
      true
      }
    }
  }
