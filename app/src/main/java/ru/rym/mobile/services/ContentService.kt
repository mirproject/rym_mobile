package ru.rym.mobile.services

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import ru.rym.mobile.R
import ru.rym.mobile.config.ConfigApp
import ru.rym.mobile.entities.response.PublicationApiResponse
import ru.rym.mobile.entities.response.TopPublicationsApiResponse
import ru.rym.mobile.entities.response.UserApiResponse
import java.util.*

class ContentService {

    fun getTopContent(
        token: String,
        countTop: Int,
        context: Context
    ): TopPublicationsApiResponse {

        val users = mutableListOf<UserApiResponse>()
        users.add(
            UserApiResponse(
                userUUID = token,
                userName = "user1",
                userPhone = "79991112233",
                email = "user1@mail.ru",
                fullName = "user1",
                groupName = "user",
                userBlocked = false,
                commentOnChange = "",
                fileUUID = null,
                imageBitmap = null,
                userSubscribers = mutableListOf<String>(),
                userLikes = mutableListOf<String>(),
                userFavorites = mutableListOf<String>()
            )
        )
        users.add(
            UserApiResponse(
                userUUID = UUID.randomUUID().toString(),
                userName = "user2",
                userPhone = "79991112244",
                email = "user2@mail.ru",
                fullName = "user2",
                groupName = "user",
                userBlocked = false,
                commentOnChange = "",
                fileUUID = null,
                imageBitmap = null,
                userSubscribers = mutableListOf<String>(),
                userLikes = mutableListOf<String>(),
                userFavorites = mutableListOf<String>()
            )
        )

        val publications = mutableListOf<PublicationApiResponse>()
        publications.add(
            PublicationApiResponse(
                userUUID = token,
                contentUUID = "",
                contentType = "IMAGE",
                userName = "user1",
                description = "user1",
                coordinates = "",
                categories  = "",
                fileUUID = "",
                thumbnailFileUUID  = "",
                created  = "",
                updated  = "",
                imageBitmapThumbnail = BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile),
                imageBitmapBase = BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile),
                imageBitmapUser = BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile)
            )
        )

        return TopPublicationsApiResponse(
                publications = publications,
                users = users
            )
    }

    fun getFileContent(
        token: String,
        fileUUID: String
    ): ByteArray {
        val responseJson = HttpService.sendAuthGetRequestFile(
            token = token,
            requestUrl = "http://${ConfigApp.identityHost}/storage/api/v1/content/content-file/$fileUUID"
        )
        return responseJson
    }

    fun getContentFileUri(
        token: String,
        fileUUID: String
    ): Uri {
        val response = "http://${ConfigApp.identityHost}/storage/api/v1/content/content-file/$fileUUID"
        return Uri.parse(response)
    }

}