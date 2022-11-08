package ru.rym.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 *
 * @date 22.06.2022
 * @author skyhunter
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthUserApiResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresSeconds: Int,
    val userUUID: String
)
