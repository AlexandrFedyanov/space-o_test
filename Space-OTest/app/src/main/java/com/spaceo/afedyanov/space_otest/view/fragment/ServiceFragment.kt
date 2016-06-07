package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R

/**
 * Created by Alexandr on 06.06.2016.
 */
class ServiceFragment: BaseFragment() {


    companion object {
        fun newInstance() : ServiceFragment {
            return ServiceFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_service, container, false)
    }

}