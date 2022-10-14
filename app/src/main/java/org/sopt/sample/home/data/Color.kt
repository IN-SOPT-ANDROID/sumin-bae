package org.sopt.sample.home.data

import androidx.annotation.DrawableRes

data class Color(
    @DrawableRes
    val image: Int,
    val name: String,
    val hex: String,
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_COLOR = 1
    }
}
