package com.alessiocameroni.revomusicplayer.util.functions

import java.util.*
import kotlin.math.roundToInt

fun calculateSongDuration(duration: Int?): String {
    val fixedDuration: Double = (duration ?: 0).toDouble() / 1000

    var minutes: Double = fixedDuration / 60
    var seconds: Int = ((minutes - minutes.toInt()) * 60).roundToInt()
    when(seconds) {
        60 -> {
            seconds = 0
            minutes++
        }
    }

    return String.format(
        Locale.US,
        "%02d:%02d",
        minutes.toInt(),
        seconds
    )
}