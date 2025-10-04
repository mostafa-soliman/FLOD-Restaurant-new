package com.fadali.flod_restaurant.core.util

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocalDate.toUTCFormat(): String {
    val utcDate = this.atStartOfDay().atOffset(ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'")
    return utcDate.format(formatter)
}
