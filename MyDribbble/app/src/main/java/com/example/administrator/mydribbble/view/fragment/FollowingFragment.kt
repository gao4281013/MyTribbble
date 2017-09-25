package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.administrator.mydribbble.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FollowingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPresses() {
        super.onBackPresses()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
        super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
