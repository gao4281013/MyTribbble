package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.administrator.mydribbble.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BunketsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BunketsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BunketsFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_bunkets, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
         val TYPE = "type"
        val SHOT_ID = "id"
        val LOCK_BUCKET = "lock"
        val ADD_SHOT = "add"
        fun newInstance(type: String? = null, shotId:Long=0): BunketsFragment {
            val fragment = BunketsFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            args.putLong(SHOT_ID, shotId)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
