package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.mydribbble.R

class BucketShotsActivity : AppCompatActivity() {
 companion object {
    val KEY_ID ="id"
    val KEY_TITLE = "title"
 }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_bucket_shots)
  }
}
