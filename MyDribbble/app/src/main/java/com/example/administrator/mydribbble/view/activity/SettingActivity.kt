package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.view.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        fragmentManager.beginTransaction().replace(R.id.mContentLayout,SettingFragment()).commit()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        tool_bar.setNavigationOnClickListener { finish() }
    }
}
