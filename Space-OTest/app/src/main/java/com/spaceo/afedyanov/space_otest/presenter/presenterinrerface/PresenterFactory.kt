package com.spaceo.afedyanov.space_otest.presenter.presenterinrerface

/**
 * Created by Alexandr on 09.06.2016.
 */
interface PresenterFactory <T> {

    fun createPresenter(): T
}