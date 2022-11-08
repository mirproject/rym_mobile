package ru.rym.mobile.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.rym.mobile.databinding.ActivityContentBinding
import ru.rym.mobile.entities.dto.SelectedContentDTO
import ru.rym.mobile.entities.dto.UserEntityDTO
import ru.rym.mobile.entities.dto.UsersDTO
import ru.rym.mobile.entities.response.AuthUserApiResponse
import ru.rym.mobile.fragments.BaseMenuFragment
import ru.rym.mobile.services.ContentService
import ru.rym.mobile.services.UserService
import java.util.*

class ContentActivity : AppCompatActivity() {

    private lateinit var activityContentBinding: ActivityContentBinding
    private val userService: UserService = UserService()
    private val contentService: ContentService = ContentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContentBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(activityContentBinding.root)

        BaseMenuFragment.newInstance("content")

        //val selectedPublication = SelectedContentDTO.publication!!
        val userUUID = UUID.randomUUID().toString()
        val userResponse = userService.getUser(userUUID,"")
        UserEntityDTO.setUserEntity(
            AuthUserApiResponse(
                accessToken = "",
                refreshToken = "",
                tokenType = "",
                expiresSeconds = 0,
                userUUID = userResponse.userUUID
            ) , userResponse)

        //Загрузка информации о контенте и пользователях в одном пакете
        val contentListResponse = contentService.getTopContent(userResponse.userUUID, 10, this)
        UsersDTO.users = contentListResponse.users.toMutableList()
        for (contentResponse in contentListResponse.publications) {
            SelectedContentDTO.publication = contentResponse
        }
    }

}