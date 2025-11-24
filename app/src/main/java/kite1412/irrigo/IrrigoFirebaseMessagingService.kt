package kite1412.irrigo

import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kite1412.irrigo.data.backend.firebase.DeviceTokenRegister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessagingService as _FirebaseMessagingService

@AndroidEntryPoint
class IrrigoFirebaseMessagingService : _FirebaseMessagingService() {

    private val logTag = "IrrigoFirebaseMessagingService"

    @Inject
    lateinit var deviceTokenRegister: DeviceTokenRegister

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenRegister(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(logTag, "title: ${message.notification?.title}, body: ${message.notification?.body}")
    }
}