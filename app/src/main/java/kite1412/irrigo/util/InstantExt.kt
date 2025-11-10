package kite1412.irrigo.util

import android.os.Build
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

fun now() = Clock.System.now()

fun Instant.toLocalDateTime() =
    toLocalDateTime(TimeZone.currentSystemDefault())

fun Instant.getLocalInstantInfo(
    dateFormat: String = "d MMMM yyyy",
    timeFormat: String = "H:mm"
): LocalInstantInfo = with(toLocalDateTime()) {
    val formatter = DateTimeFormatter.ofPattern(
        dateFormat,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            Locale.of("id", "ID")
        } else {
            Locale("id", "ID")
        }
    )
    val timeFormatter = DateTimeFormatter.ofPattern(timeFormat)
    val now = now()

    LocalInstantInfo(
        day = if (now.toLocalDateTime().date == date) "Hari Ini"
        else if ((now - 1.days).toLocalDateTime().date == date) "Kemarin"
        else formatter.format(toJavaLocalDateTime()),
        time = timeFormatter.format(toJavaLocalDateTime())
    )
}