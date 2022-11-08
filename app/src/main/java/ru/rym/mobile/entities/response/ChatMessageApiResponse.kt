package ru.rym.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatMessageApiResponse (

    val chatMessages: List<ChatMessageApiModel>
)