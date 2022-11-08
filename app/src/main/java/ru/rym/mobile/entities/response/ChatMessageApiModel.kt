package ru.rym.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatMessageApiModel (

    val messageUUID: String,
    val messageUserUUID: String,
    val messagePayload: String,
    val messageChatUUID: String,
    val createdDate: String,
    val createdTime: String

)