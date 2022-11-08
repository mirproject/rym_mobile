package ru.rym.mobile.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import ru.rym.mobile.config.ConfigApp
import ru.rym.mobile.entities.response.ChatMessageApiModel
import ru.rym.mobile.entities.response.ChatMessageApiResponse
import java.text.SimpleDateFormat
import java.util.*

class ChatService {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    fun createChatMessage(
        token: String,
        userUUID: String,
        contentUUID: String,
        payload: String
    ): ChatMessageApiModel {

        return ChatMessageApiModel(
            messageUUID = UUID.randomUUID().toString(),
            messageUserUUID = userUUID,
            messagePayload = payload,
            messageChatUUID = contentUUID,
            createdDate = getStringFromDateTimeSimpleFormat(Date()),
            createdTime = getStringFromDateTimeSimpleFormat(Date())
        )
    }

    fun getChatMessage(
        token: String,
        contentUUID: String
    ): ChatMessageApiResponse {
        val responseJson = HttpService.sendAuthGetRequest(
            token = token,
            requestUrl = "http://${ConfigApp.identityHost}/storage/api/v1/chat/message/$contentUUID"
        )
        return mapper.readValue(responseJson, ChatMessageApiResponse::class.java)
    }

    fun getStringFromDateTimeSimpleFormat(dateSimple: Date): String {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return timeFormat.format(dateSimple)
    }

}