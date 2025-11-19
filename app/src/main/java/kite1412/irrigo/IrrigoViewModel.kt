package kite1412.irrigo

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kite1412.irrigo.ui.util.AppBarUpdater
import javax.inject.Inject

@HiltViewModel
class IrrigoViewModel @Inject constructor() : ViewModel() {
    var appBarSubtitle by mutableStateOf<String?>(null)
        private set

    val appBarUpdater = object : AppBarUpdater {
        override fun setSubtitle(subtitle: String) {
            appBarSubtitle = subtitle
        }

        override fun dismissSubtitle() {
            appBarSubtitle = null
        }
    }
    val snackbarHostState = SnackbarHostState()
}