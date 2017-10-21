package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.mydribbble.R

class SearchActivity : AppCompatActivity() {

  companion object {
    val KEY_KEYWORD:String = "keyword"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
  }
}
