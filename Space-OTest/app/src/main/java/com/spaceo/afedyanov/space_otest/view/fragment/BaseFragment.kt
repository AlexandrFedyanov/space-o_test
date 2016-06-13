package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment: Fragment() {

    open fun scrollContentToTop() {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    abstract fun setupLayout()
}