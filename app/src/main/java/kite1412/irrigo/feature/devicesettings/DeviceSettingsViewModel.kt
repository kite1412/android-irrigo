package kite1412.irrigo.feature.devicesettings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kite1412.irrigo.domain.WateringRepository
import javax.inject.Inject

@HiltViewModel
class DeviceSettingsViewModel @Inject constructor(
    private val wateringRepository: WateringRepository
) : ViewModel() {

}