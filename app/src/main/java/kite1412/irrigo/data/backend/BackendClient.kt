package kite1412.irrigo.data.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kite1412.irrigo.BuildConfig
import kite1412.irrigo.data.backend.dto.response.ApiResponse
import kite1412.irrigo.data.backend.util.BackendResult
import kite1412.irrigo.data.backend.util.WebSocketMessageType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

abstract class BackendClient {
    protected val logTag = "BackendClient"

    protected val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(WebSockets)

        defaultRequest {
            url(BuildConfig.SERVER_URL)
            contentType(ContentType.Application.Json)
        }
    }

    protected fun <T> responseOrThrow(res: ApiResponse<T>): ApiResponse<T> =
        if (res.success) res
        else throw RuntimeException(res.message ?: "Unknown error")

    /**
     * @param path /<path name>
     */
    protected suspend inline fun <reified T> get(
        path: String,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): BackendResult<T> =
        try {
            val res = responseOrThrow(
                client.get(path, builder)
                    .body<ApiResponse<T>>()
            )

            BackendResult.Success(
                data = res.data,
                message = res.message
            )
                .also {
                    Log.d(logTag, "$path: success, message: ${it.message}, data: ${it.data}")
                }
        } catch (e: Exception) {
            Log.e(logTag, "GET $path: error, message: ${e.message}")
            BackendResult.Error(
                message = e.message ?: "Unknown error",
                throwable = e
            )
        }

    /**
     * @param path /<path name>
     * @param body request body
     */
    protected suspend inline fun <reified T, reified R> post(
        path: String,
        body: T? = null
    ): BackendResult<R> = try {
        val res = responseOrThrow(
            client.post {
                url(path)
                body.let(::setBody)
            }.body<ApiResponse<R>>()
        )

        BackendResult.Success(
            data = res.data,
            message = res.message
        )
    } catch (e: Exception) {
        Log.e(logTag, "POST $path: error, message: ${e.message}")
        BackendResult.Error(
            message = e.message ?: "Unknown error",
            throwable = e
        )
    }

    /**
     * @param path /<path name>
     * @param body request body
     */
    protected suspend inline fun <reified T, reified R> patch(
        path: String,
        body: T? = null
    ): BackendResult<R> = try {
        val res = responseOrThrow(
            client.patch {
                url(path)
                body.let(::setBody)
            }.body<ApiResponse<R>>()
        )

        BackendResult.Success(
            data = res.data,
            message = res.message
        )
    } catch (e: Exception) {
        Log.e(logTag, "PATCH $path: error, message: ${e.message}")
        BackendResult.Error(
            message = e.message ?: "Unknown error",
            throwable = e
        )
    }

    protected fun observeMessages(type: WebSocketMessageType): Flow<JsonObject> = channelFlow {
        val session = client.webSocketSession(BuildConfig.SERVER_URL_WS)

        launch {
            for (frame in session.incoming) {
                if (frame is Frame.Text) {
                    val json = Json
                        .parseToJsonElement(frame.readText())
                        .jsonObject

                    if (type.value == json["type"]?.jsonPrimitive?.content) {
                        send(
                            JsonObject(
                                json.filterKeys { k -> k != "type" }
                            )
                        )
                    }
                }
            }
        }

        awaitClose {
            launch {
                session.close()
            }
        }
    }
}