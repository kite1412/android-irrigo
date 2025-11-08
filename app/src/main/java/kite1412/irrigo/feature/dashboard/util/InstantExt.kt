package kite1412.irrigo.feature.dashboard.util

import android.os.Build
import kite1412.irrigo.util.now
import kite1412.irrigo.util.toLocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

fun Instant.getLocalInstantInfo(): LocalInstantInfo = with(toLocalDateTime()) {
    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            Locale.of("id", "ID")
        } else {
            Locale("id", "ID")
        }
    )
    val timeFormatter = DateTimeFormatter.ofPattern("H:mm")
    val now = now()

    LocalInstantInfo(
        day = if (now.toLocalDateTime().date == date) "Hari Ini"
            else if ((now - 1.days).toLocalDateTime().date == date) "Kemarin"
            else formatter.format(toJavaLocalDateTime()),
        time = timeFormatter.format(toJavaLocalDateTime())
    )
}