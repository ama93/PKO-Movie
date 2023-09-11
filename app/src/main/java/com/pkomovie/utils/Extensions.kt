package com.pkomovie.utils

import java.util.Locale

fun Double.round(decimalPlaces: Int) = "%.${decimalPlaces}f".format(Locale.ENGLISH, this).toDouble()

fun String.removeWhitespaces() = replace("\\s".toRegex(), "")
