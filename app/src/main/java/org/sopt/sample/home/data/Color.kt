package org.sopt.sample.home.data

data class Color(
    val name: String,
    val hex: String,
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_COLOR = 1
    }
}
