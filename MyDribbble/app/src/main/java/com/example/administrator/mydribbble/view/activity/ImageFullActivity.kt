package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.mydribbble.R

class ImageFullActivity : AppCompatActivity() {
  companion object {
    val KEY_URL_NORMAL:String ="key_url_normal"
    val KEY_URL_LOW:String = "KEY_URL_LOW"
    val KEY_TITLE:String = "key_title"
  }



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_full)
  }
}
