package kite1412.irrigo.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

fun now() = Clock.System.now()

fun Instant.toLocalDateTime() =
    toLocalDateTime(TimeZone.currentSystemDefault())