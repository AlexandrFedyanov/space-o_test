package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.showImageActivitySelectPicture
import com.spaceo.afedyanov.space_otest.appnavigation.showImageActivityTakePicture
import kotlinx.android.synthetic.main.fragment_image.*

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
        takePictureButton.setOnClickListener({ showImageActivityTakePicture() })
        selectPictureButton.setOnClickListener({ showImageActivitySelectPicture() })
    }

}