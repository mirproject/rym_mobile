package ru.rym.mobile.services

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.rym.mobile.exceptions.RymException
import java.net.URL


object HttpService {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    fun createJsonRequest(requestObject: Any): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestJson = mapper.writeValueAsString(requestObject)
        return requestJson.toRequestBody(mediaType)
    }

    private fun callRequest(request: Request): String {

        val httpResponse = OkHttpClient().newCall(request).execute()
        if (httpResponse != null && httpResponse.code != 200) {
            Log.e("Error sendPostRequest", "httpResponse code: ${httpResponse.code}")
            throw RymException(httpResponse.message)
        }
        return httpResponse.body!!.string()
    }
    //TODO
    private fun callFIleRequest(request: Request): ByteArray {

        val httpResponse = OkHttpClient().newCall(request).execute()
        if (httpResponse != null && httpResponse.code != 200) {
            Log.e("Error sendPostRequest", "httpResponse code: ${httpResponse.code}")
            throw RymException(httpResponse.message)
        }
        return httpResponse.body!!.bytes()
    }

    fun sendPostRequest(requestBody: RequestBody, requestUrl: String): String {

        Log.i("sendPostRequest", "requestUrl: $requestUrl")

        return callRequest(
            Request.Builder()
             .post(requestBody)
             .url(URL(requestUrl))
             .build()
        )
    }

    fun sendAuthGetRequest(token: String, requestUrl: String): String {
        Log.i("sendAuthGetRequest", "requestUrl: $requestUrl")
        return callRequest(
            Request.Builder()
                .get()
                .addHeader("Authorization", "Bearer $token")
                .url(URL(requestUrl))
                .build()
        )
    }

    fun sendAuthGetRequestFile(token: String, requestUrl: String): ByteArray {
        Log.i("sendAuthGetRequest", "requestUrl: $requestUrl")

        return callFIleRequest(
            Request.Builder()
                .get()
                .addHeader("Authorization", "Bearer $token")
                .url(URL(requestUrl))
                .build()
        )

    }

    fun sendPostAuthRequest(token: String, requestBody: RequestBody, requestUrl: String): String {

        Log.i("sendPostAuthRequest", "requestUrl: $requestUrl")

        return callRequest(
            Request.Builder()
                .post(requestBody)
                .addHeader("Authorization", "Bearer $token")
                .url(URL(requestUrl))
                .build()
        )
    }

    fun sendDeleteAuthRequest(
        token: String,
        requestUrl: String
    ): String {

        Log.i("sendPostAuthRequest", "requestUrl: $requestUrl")

        return callRequest(
            Request.Builder()
                .delete()
                .addHeader("Authorization", "Bearer $token")
                .url(URL(requestUrl))
                .build()
        )
    }
}