package ru.rym.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TopUsersApiResponse (

    val users: List<UserApiResponse>

)