package ru.rym.mobile.usecases

import android.app.Activity
import android.widget.LinearLayout
import android.widget.ScrollView
import ru.rym.mobile.utils.AlertDialogUtil
import ru.rym.mobile.entities.LogEventTypeEnum
import ru.rym.mobile.entities.dto.UserEntityDTO
import ru.rym.mobile.entities.response.PublicationApiResponse
import ru.rym.mobile.exceptions.RymException
import ru.rym.mobile.services.ChatService
import ru.rym.mobile.services.UserService
import ru.rym.mobile.utils.ImageUtil

class CreateMessageUsecase {

    private val chatService: ChatService = ChatService()
    private val userService: UserService = UserService()

    @Throws(RymException::class)
    fun execute(
        selectedPublication: PublicationApiResponse,
        contentActivity: Activity,
        messagePayload: String,
        chatMessageLayout: LinearLayout,
        chatMessageScroll: ScrollView
    ) {

        userService.createLog(UserEntityDTO.accessToken!!, UserEntityDTO.userUUID.toString(), LogEventTypeEnum.ADD_CHAT_MESSAGE, "{ \"message\":\"$messagePayload\" }")

        val response = try {
            chatService.createChatMessage(
                token = UserEntityDTO.accessToken!!,
                contentUUID = selectedPublication.contentUUID,
                userUUID = UserEntityDTO.userUUID.toString(),
                payload = messagePayload
            )
        } catch (e: java.lang.Exception) {
            AlertDialogUtil.showErrorMessage(contentActivity, e.message)
            return
        }
        selectedPublication.chatMessages.add(response)

        ImageUtil.addMessageToPanel(
            chatMessageLayout,
            chatMessageScroll,
            selectedPublication.chatMessages,
            contentActivity
        )

    }



}