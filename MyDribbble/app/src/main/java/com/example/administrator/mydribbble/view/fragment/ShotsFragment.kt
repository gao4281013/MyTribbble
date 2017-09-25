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
 * [ShotsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShotsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShotsFragment : BaseFragment() {


    companion object {
        val SORT = "sort"
        val RECENT = "recent"
        fun newInstance(sort:String?=null):ShotsFragment{
            val fragment = ShotsFragment()
            val args = Bundle()
            args.putString(SORT,sort)
            fragment.arguments = args
            return fragment
        }
    }


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
