package com.example.administrator.mydribbble.view.fragment

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.view.activity.DetailsActivity
import kotlinx.android.synthetic.main.search_layout.*

class BaseFragment : Fragment() {
    var isShowSearchBar:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    open fun onBackPresses(){}

    open fun onKeyDown(keyCode:Int,event: KeyEvent?){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.VOICE_CODE && resultCode ==Activity.RESULT_OK){
            val  keywords = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            keywords?.forEach {
               mSearch_edit.setText(it)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun startDetailsActivity(){
        startActivity(Intent(activity,DetailsActivity::class.java),
        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }


}
