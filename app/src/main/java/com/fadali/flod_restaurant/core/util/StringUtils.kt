package com.fadali.flod_restaurant.core.util

fun String.isNotBlackOrEmpty(): Boolean {
    return this.isNotEmpty() && this.isNotBlank()
}

fun String.isOnlyLettersAndSpaces(): Boolean {
    return this.matches("^[\\p{L} ]+$".toRegex())
}
