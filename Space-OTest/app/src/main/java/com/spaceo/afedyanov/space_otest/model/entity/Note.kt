package com.spaceo.afedyanov.space_otest.model.entity

import java.io.Serializable

class Note: Serializable{
    var _id: Long? = null
    var name: String? = null
    var isChecked: Boolean = false
}