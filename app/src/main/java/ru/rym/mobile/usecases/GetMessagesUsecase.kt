package ru.rym.mobile.usecases

import android.app.Activity
import android.widget.LinearLayout
import android.widget.ScrollView
import ru.rym.mobile.utils.AlertDialogUtil
import ru.rym.mobile.entities.dto.UserEntityDTO
import ru.rym.mobile.entities.response.PublicationApiResponse
import ru.rym.mobile.exceptions.RymException
import ru.rym.mobile.services.ChatService
import ru.rym.mobile.utils.ImageUtil

class GetMessagesUsecase {

    private val chatService: ChatService = ChatService()

    @Throws(RymException::class)
    fun execute(
        selectedPublication: PublicationApiResponse,
        contentActivity: Activity,
        chatMessageLayout: LinearLayout,
        chatMessageScroll: ScrollView
    ) {
        val response = try {
            chatService.getChatMessage(
                token = UserEntityDTO.accessToken!!,
                contentUUID = selectedPublication.contentUUID
            )
        } catch (e: java.lang.Exception) {
            AlertDialogUtil.showErrorMessage(contentActivity, e.message)
            return
        }
        selectedPublication.chatMessages = response.chatMessages.toMutableList()

        ImageUtil.addMessageToPanel(
            chatMessageLayout,
            chatMessageScroll,
            selectedPublication.chatMessages,
            contentActivity
        )

    }

}