package ru.rym.mobile.utils

import android.content.Context
import android.text.Editable
import android.util.DisplayMetrics
import android.widget.EditText

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun Int.toPx(context: Context) = this * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT

fun EditText.setEditableText(text:String){ this.text = Editable.Factory.getInstance().newEditable(text) }