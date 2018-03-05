package com.material.hanmo.material_design_strudy

import android.content.Context

/**
 * Created by hanmo on 2018. 3. 5..
 */
class Place(val name: String, private val imageName: String, val isFav: Boolean = false) {
    fun getImageResourceId(context: Context): Int {
        return context.resources.getIdentifier(this.imageName, "drawable", context.packageName)
    }
}