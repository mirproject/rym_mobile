package ru.rym.mobile.utils

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.rym.mobile.R

object AlertDialogUtil {

    fun showErrorMessage(context: Context?, errorText : String?) {
        val errorMessage = initErrorDialog(context, errorText, null)
        errorMessage.show()
    }

    fun showErrorMessage(context: Context?, errorText : String?, progress: AlertDialog) {
        if (context is AppCompatActivity)
            context.runOnUiThread(Runnable {
                val errorMessage = initErrorDialog(context, errorText, progress)
                errorMessage.show()
            })

    }

    fun initProgressDialog(context: Context, message:String): AlertDialog {

        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.START
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.START
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.WHITE)
        tvText.textSize = 14.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }

    private fun initErrorDialog (context: Context?, errorMessage : String?, progress: AlertDialog?): AlertDialog {
        progress?.dismiss()
        val builder = context?.let { AlertDialog.Builder(it) }
        val dialogView = LayoutInflater.from(context).inflate(R.layout.error_message_view, null, false)
        builder?.setView(dialogView)
        dialogView.findViewById<TextView>(R.id.tv_error_message).text = errorMessage
        dialogView.findViewById<LinearLayout>(R.id.ll_background).background = context?.getDrawable(R.color.red_600)
        dialogView.findViewById<ImageView>(R.id.iv_error_icon).setImageResource(R.drawable.error_icon)
        val alertDialog = builder?.create()
        alertDialog?.window?.setGravity(Gravity.TOP)
        alertDialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialogView.findViewById<ImageView>(R.id.iv_error_close).setOnClickListener(View.OnClickListener {
            alertDialog?.dismiss()
        })
        return alertDialog!!
    }

}