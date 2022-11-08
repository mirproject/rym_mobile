package ru.rym.mobile.usecases

import android.util.Log
import android.widget.TextView
import ru.rym.mobile.entities.LogEventTypeEnum
import ru.rym.mobile.entities.dto.UserEntityDTO
import ru.rym.mobile.exceptions.RymException
import ru.rym.mobile.services.UserService

/**
 *
 * Отправка всех исключений
 * @date 28.10.2022
 * @author skyhunter
 *
 */
class SendErrorUsecase {

    private val userService: UserService = UserService()

    @Throws(RymException::class)
    fun execute(error: String, textView: TextView) {
        Log.e("Error", error)
        textView.apply {
            text = error
        }
        userService.createLog(
            UserEntityDTO.accessToken!!,
            UserEntityDTO.userUUID.toString(),
            LogEventTypeEnum.ERROR,
            "{ \"Error\":\"$error\" }"
        )
    }

}