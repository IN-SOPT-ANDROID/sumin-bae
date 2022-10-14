package org.sopt.sample.home

import androidx.lifecycle.ViewModel
import org.sopt.sample.R
import org.sopt.sample.home.data.Color

class HomeViewModel : ViewModel() {
    val blueColorList = listOf<Color>(
        Color(
            image = R.drawable.cold_mist,
            name = "Cold Mist",
            hex = "#EFF8FE"
        ),
        Color(
            image = R.drawable.baby_blue,
            name = "Baby Blue",
            hex = "#BBE1F5"
        ),
        Color(
            image = R.drawable.afternoon_blue,
            name = "Afternoon Blue",
            hex = "#89C3E7"
        ),
        Color(
            image = R.drawable.brisk_blue,
            name = "Brisk Blue",
            hex = "#57C3EA"
        ),
        Color(
            image = R.drawable.comet_blue,
            name = "Comet Blue",
            hex = "#3AB0E6"
        ),
        Color(
            image = R.drawable.egyptian_blue,
            name = "Egyptian Blue",
            hex = "#0081B2"
        ),
        Color(
            image = R.drawable.milky_blue,
            name = "Milky Blue",
            hex = "#EAF1FA"
        ),
        Color(
            image = R.drawable.frosted_blue,
            name = "Frosted Blue",
            hex = "#AFC3E1"
        ),
        Color(
            image = R.drawable.nostalgic_blue,
            name = "Nostalgic Blue",
            hex = "#6781B2"
        ),
        Color(
            image = R.drawable.foggy_blue,
            name = "Foggy Blue",
            hex = "#9FB9D0"
        ),
        Color(
            image = R.drawable.light_navy_blue,
            name = "Light Navy Blue",
            hex = "#7194B8"
        ),
        Color(
            image = R.drawable.forest_night,
            name = "Forest Night",
            hex = "#4B5A6B"
        )
    )
}