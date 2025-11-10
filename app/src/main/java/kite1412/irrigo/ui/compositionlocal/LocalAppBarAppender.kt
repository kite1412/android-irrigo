package kite1412.irrigo.ui.compositionlocal

import androidx.compose.runtime.compositionLocalOf
import kite1412.irrigo.ui.util.AppBarUpdater

val LocalAppBarUpdater = compositionLocalOf<AppBarUpdater> {
    object : AppBarUpdater {
        override fun setSubtitle(subtitle: String) {}
        override fun dismissSubtitle() {}
    }
}