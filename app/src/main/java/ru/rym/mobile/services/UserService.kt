package ru.rym.mobile.services

import android.util.Log
import android.webkit.MimeTypeMap
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.rym.mobile.config.ConfigApp
import ru.rym.mobile.entities.LogEventTypeEnum
import ru.rym.mobile.entities.response.TopUsersApiResponse
import ru.rym.mobile.entities.response.UploadResultApiResponse
import ru.rym.mobile.entities.response.UserApiResponse
import ru.rym.mobile.exceptions.RymException
import java.io.File
import java.util.*

class UserService {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    fun uploadUserFile(
        token: String,
        sourceFile: File,
        uploadedFileName: String,
        userUUID: String,
        contentType: String
    ): UploadResultApiResponse {
        return UploadResultApiResponse(
            contentUUID = UUID.randomUUID().toString(),
            contentStatus = "Ok"
        )
    }

    fun getUser(
        userUUID: String,
        token: String
    ): UserApiResponse {
        return UserApiResponse(
            userUUID = userUUID,
            userName = "admin",
            userPhone = "79991112233",
            email = "admin@mail.ru",
            fullName = "admin",
            groupName = "user",
            userBlocked = false,
            commentOnChange = "",
            fileUUID = null,
            imageBitmap = null,
            userSubscribers = mutableListOf<String>(),
            userLikes = mutableListOf<String>(),
            userFavorites = mutableListOf<String>()
        )
    }

    fun getTopUsers(
        token: String
    ): TopUsersApiResponse {
        val responseJson = HttpService.sendAuthGetRequest(
            token = token,
            requestUrl = "http://${ConfigApp.identityHost}/identifier/api/v1/users/top"
        )
        return mapper.readValue(responseJson, TopUsersApiResponse::class.java)
    }

    fun createLog(
        token: String,
        userUUID: String,
        logEventType: LogEventTypeEnum,
        payload: String
    ) {

    }

    private fun createFileRequest(
        sourceFile: File,
        uploadedFileName: String,
        userUUID: String,
        contentType: String
    ): RequestBody {
        val mimeType = getMimeType(sourceFile)
        if (mimeType == null) {
            Log.e("file error", "Not able to get mime type")
            throw RymException("Not able to get mime type")
        }
        return MultipartBody
            .Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("uploadContent", uploadedFileName, sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
            .addFormDataPart("userUUID", userUUID)
            .addFormDataPart("contentType", contentType)
        .build()
    }

    private fun getMimeType(file: File): String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        return if (extension != null) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } else {
            null
        }
    }

}