package com.spaceo.afedyanov.space_otest.view.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.listener.NotesFragmentListener

/**
 * Created by Alexandr on 06.06.2016.
 */
class ImageFragment: BaseFragment() {


    companion object {
        fun newInstance() : ImageFragment {
            return ImageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_image, container, false)
    }

    override fun setupLayout() {

    }

}