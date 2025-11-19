package kite1412.irrigo

import kite1412.irrigo.data.backend.BackendClient
import kite1412.irrigo.data.backend.util.WebSocketMessageType
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BackendClientTest {
    private class Client : BackendClient() {
        fun observe(type: WebSocketMessageType) =
            observeMessages(type)
    }
    private val client = Client()

    @Test
    fun observeMessages_isSuccess() {
        runBlocking {
            var receiveCount = 0
            launch {
                client
                    .observe(WebSocketMessageType.SOIL_MOISTURE_LOG)
                    .collect {
                        println(it)
                        receiveCount++
                        cancel()
                    }
            }
            delay(5000)
            assert(receiveCount > 0)
        }
    }
}