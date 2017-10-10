package com.example.administrator.mydribbble.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.presenter.ShotPresenter
import com.example.administrator.mydribbble.view.api.IShotView
import kotlin.reflect.KProperty

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShotsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShotsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShotsFragment : BaseFragment(),IShotView {
    private val mPresenter:ShotPresenter by lazy {
        ShotPresenter(this)
    }

    private var mSort:String?= null
    private var mSortList:String? = null
    private var mTimeFrame:String? = null
    private var mPage:Int = 1
    private var mShots:MutableList<Shot> by lazy {
        mutableListOf<Shot>()
    }
    private var isLoading:Boolean = false;


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

    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {

    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
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

private operator fun Any.setValue(shotsFragment: ShotsFragment, property: KProperty<*>, mutableList: MutableList<Shot>) {}
