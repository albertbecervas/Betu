package com.lbcompany.betu.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lbcompany.betu.R


/**
 * A simple [Fragment] subclass.
 */
class GroupalBetsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_groupal_bets, container, false)
    }

}// Required empty public constructor
