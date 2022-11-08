package ru.rym.mobile.entities.response

import android.graphics.Bitmap
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PublicationApiResponse (

    val userUUID: String,
    val contentUUID: String,
    val contentType: String,
    val userName: String,
    val description: String?,
    val coordinates: String?,
    val categories: String?,
    val fileUUID: String?,
    val thumbnailFileUUID: String?,
    val created: String,
    val updated: String,

    var imageBitmapThumbnail: Bitmap?,
    var imageBitmapBase: Bitmap?,
    var imageBitmapUser: Bitmap?,

    var chatMessages: MutableList<ChatMessageApiModel> = mutableListOf()
)