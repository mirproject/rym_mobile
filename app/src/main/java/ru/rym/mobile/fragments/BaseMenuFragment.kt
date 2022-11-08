package ru.rym.mobile.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.rym.mobile.R
import ru.rym.mobile.activities.ContentActivity
import ru.rym.mobile.entities.dto.SelectedContentDTO
import ru.rym.mobile.usecases.CreateMessageUsecase
import ru.rym.mobile.usecases.GetMessagesUsecase

/**
 * A simple [Fragment] subclass.
 * Use the [BaseMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BaseMenuFragment : Fragment() {

    private var menuButton: ImageButton? = null
    private var mainActivity: AppCompatActivity? = null
    private var bottomMenu: FrameLayout? = null
    private var chatButton: ImageButton? = null
    private val getMessagesUsecase: GetMessagesUsecase = GetMessagesUsecase()
    private val createMessageUsecase: CreateMessageUsecase = CreateMessageUsecase()

    private var chatLayout: LinearLayout? = null
    private var chatMessageLayout: LinearLayout? = null
    private var sendToLayout: LinearLayout? = null
    private var chatEditText: EditText? = null
    private var chatMessageScroll: ScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //arguments?.let {
        //    param1 = it.getString(ARG_PARAM1)
        //}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View =  inflater.inflate(R.layout.fragment_base_menu, container, false)

        val activity = activity
        if (activity is AppCompatActivity) {
           mainActivity = activity
        }

       bottomMenu = view.findViewById(R.id.bottom_panel_menu)
       menuButton = view.findViewById(R.id.main_button)

        bottomMenu = view.findViewById(R.id.bottom_panel_menu)
        bottomMenu!!.visibility = if (activity is ContentActivity) {
            activateContentButtons(view, activity)
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        return view
    }

    private fun activateContentButtons(view: View, contentActivity: Activity) {
        val selectedPublication = SelectedContentDTO.publication!!
        chatLayout = contentActivity.findViewById(R.id.content_chat_layout)
        chatEditText = contentActivity.findViewById(R.id.content_comment_editText)
        chatMessageLayout = contentActivity.findViewById(R.id.content_chat_message_layout)
        chatMessageScroll = contentActivity.findViewById(R.id.content_chat_message_scrollView)

        val chatEditText = chatEditText!!
        chatEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
             if (event.action == KeyEvent.ACTION_DOWN &&
                 (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)) {
                 createMessageUsecase.execute(
                     selectedPublication,
                     contentActivity,
                     chatEditText.text.toString(),
                     chatMessageLayout!!,
                     chatMessageScroll!!
                 )
                 chatEditText.setText("")
                 val imm: InputMethodManager = contentActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                 imm.hideSoftInputFromWindow(v.windowToken, 0)
                 chatEditText.isFocusable = false;
                 chatEditText.isFocusableInTouchMode = true;
                 return@OnKeyListener true
             }
             return@OnKeyListener false
         })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment BaseMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            BaseMenuFragment().apply {
                arguments = Bundle().apply {
                   // putString(ARG_PARAM1, param1)
                }
            }
    }
}