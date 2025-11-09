package kite1412.irrigo.feature.logs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kite1412.irrigo.feature.logs.util.LogsGroupType
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor() : ViewModel() {
    var selectedLogsGroup by mutableStateOf<LogsGroupType?>(null)
        private set

    fun updateSelectedLogsGroup(type: LogsGroupType?) {
        selectedLogsGroup = type
    }
}