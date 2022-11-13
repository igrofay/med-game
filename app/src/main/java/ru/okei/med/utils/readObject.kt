package ru.okei.med.utils

import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.*
import io.ktor.util.reflect.*
import io.ktor.websocket.*
import ru.okei.med.domain.model.Errors

suspend inline fun <reified T> DefaultClientWebSocketSession.readObject(message: Frame): T{
    val converter = converter ?: throw WebsocketConverterNotFoundException("No converter was found for websocket")
    val typeInfo = typeInfo<T>()
    val result = converter.deserialize(
        charset = call.request.headers.suitableCharset(),
        typeInfo = typeInfo,
        content = message
    )
    if (result is T) return result
    throw Errors.TransformationJson(T::class.simpleName ?: "")
}