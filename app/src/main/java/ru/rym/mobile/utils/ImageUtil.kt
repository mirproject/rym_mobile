package ru.rym.mobile.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import ru.rym.mobile.R
import ru.rym.mobile.config.ConfigApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object ImageUtil {

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getResizeImage(originalImageBitmap: Bitmap): Bitmap {

        //Если картинка вертикальная, то подгоняем по ширине
        if (originalImageBitmap.height > originalImageBitmap.width) {

            val imageBitmapResize = resizeByWidth(originalImageBitmap)
            //Если после изменения высота меньше целевой, выравниваем по высоте
            return if (imageBitmapResize.height < ConfigApp.maxImageWidth * ConfigApp.ratio) {
                resizeByHeight(imageBitmapResize)
            } else {
                imageBitmapResize
            }

            //Если картинка горизонтальная, то подгоняем по высоте
        } else {
            val imageBitmapResize = resizeByHeight(originalImageBitmap)
            //Если после изменения высота меньше целевой, выравниваем по высоте
            return if (imageBitmapResize.width < ConfigApp.maxImageWidth) {
                resizeByWidth(imageBitmapResize)
            } else {
                imageBitmapResize
            }
        }
    }
    private fun resizeByWidth(originalImageBitmap: Bitmap): Bitmap {
        val bitmapWidth = ConfigApp.maxImageWidth
        //Вычисляем процент изменения
        val factor = 100 - (bitmapWidth * 100) / originalImageBitmap.width
        //Вычисляем новую высоту
        val bitmapHeight = originalImageBitmap.height - ((originalImageBitmap.height * factor) / 100)
        return originalImageBitmap.resize(bitmapWidth, bitmapHeight)
    }

    private fun resizeByHeight(originalImageBitmap: Bitmap): Bitmap {
        val bitmapHeight = ConfigApp.maxImageWidth * ConfigApp.ratio
        //Вычисляем процент изменения
        val factor = 100 - (bitmapHeight * 100) / originalImageBitmap.height
        //Вычисляем новую ширину
        val bitmapWidth = originalImageBitmap.width - ((originalImageBitmap.width * factor) / 100)
        return originalImageBitmap.resize(bitmapWidth, bitmapHeight)
    }

    fun getBitmapOriginalFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")!!
        val fileDescriptor = parcelFileDescriptor.fileDescriptor
        val imageBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor) //.resize(ConfigApp.maxAvatarSize, ConfigApp.maxAvatarSize)
        parcelFileDescriptor.close()
        return imageBitmap
    }

    fun getMimeType(context: Context, uri: Uri) : String?{
        val cr = context.contentResolver
        return cr.getType(uri)
    }
    fun getFileName(applicationContext: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = applicationContext.contentResolver.query(uri!!, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    fun getActualPathFromUri(cashFile: File, extension: String, bitmap: Bitmap): String {

        val imageFileFolder = File(cashFile, "appName")
        if (!imageFileFolder.exists()) {
            imageFileFolder.mkdir()
        }
        var out: FileOutputStream? = null
        val imageFileName = File(imageFileFolder, "appName-${System.currentTimeMillis()}.$extension")
        try {
            out = FileOutputStream(imageFileName)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: IOException) {
            Log.e("Exception", e.message.toString())
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return imageFileName.absolutePath
    }


    fun clearTableLayout(mapTableLayout: TableLayout) {
        val count: Int = mapTableLayout.childCount
        for (i in 0 until count) {
            val child: View = mapTableLayout.getChildAt(i)
            if (child is TableRow) (child as ViewGroup).removeAllViews()
        }
    }

    fun createNewTableRow(context: Context, weightSum: Float): TableRow {
        val tableRow = TableRow(context)
        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tableRow.weightSum = weightSum
        return tableRow
    }

    fun createNewHorizontalLinearLayout(context: Context): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.layoutParams = layoutParams
        linearLayout.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        return linearLayout
    }

    fun createNewVerticalLinearLayout(context: Context): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.layoutParams = layoutParams
        return linearLayout
    }

    fun createNewFrameLayout(context: Context, layoutwidth: Int, layoutHeight: Int): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = TableRow.LayoutParams(
            0, TableRow.LayoutParams.WRAP_CONTENT, 1F
        )
        layoutParams.setMargins(5, 5, 5, 5)
        frameLayout.layoutParams = layoutParams
        frameLayout.updateLayoutParams {
            width = layoutwidth.toPx(context)
            height = layoutHeight.toPx(context)
        }
        return frameLayout
    }
    fun createImageTypeIcon(context: Context, drawable: Drawable, background: Drawable): ImageView {
        val typeIcon = ImageView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        typeIcon.setImageDrawable(drawable)
        typeIcon.layoutParams = layoutParams
        typeIcon.updateLayoutParams {
            width = 150
            height = 150
        }
        typeIcon.background = background
        return typeIcon
    }

    fun createImageIcon(context: Context, drawable: Drawable, backgroundDrawable: Drawable, imageUserBitmap: Bitmap?): ImageView {
        val imageIcon = ImageView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        if (imageUserBitmap == null) {
            imageIcon.setImageDrawable(drawable)
        } else {
            val imageIconBitmapResized = imageUserBitmap.fill(50)
            imageIcon.setImageBitmap(getRoundedBitmap(imageIconBitmapResized, 50))
            imageIcon.background = backgroundDrawable
            imageIcon.setPadding(2,2,2,2)
        }
        layoutParams.setMargins(20, 0, 0, 20)
        imageIcon.layoutParams = layoutParams
        imageIcon.updateLayoutParams {
            width = 40.toPx(context)
            height = 40.toPx(context)
        }
        return imageIcon
    }

    fun createUserIcon(context: Context, drawable: Drawable, backgroundDrawable: Drawable, imageUserBitmap: Bitmap?): ImageView {
        val imageIcon = ImageView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        if (imageUserBitmap == null) {
            imageIcon.setImageDrawable(drawable)
        } else {
            val imageIconBitmapResized = imageUserBitmap.fill(50)
            imageIcon.setImageBitmap(getRoundedBitmap(imageIconBitmapResized, 50))
            imageIcon.background = backgroundDrawable
            imageIcon.setPadding(2,2,2,2)
        }
        imageIcon.layoutParams = layoutParams
        imageIcon.updateLayoutParams {
            width = 40.toPx(context)
            height = 40.toPx(context)
        }
        return imageIcon
    }
    fun createButton(
        context: Context,
        backgroundDrawable: Drawable,
        buttonText: String,
        buttonWidth: Int,
        buttonHeight: Int
    ): Button {
        val button = Button(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.END
        )
        button.layoutParams = layoutParams
        button.updateLayoutParams {
            width = buttonWidth.toPx(context)
            height = buttonHeight.toPx(context)
        }
        button.text = buttonText
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            button.isFocusable = true
        }
        button.setTypeface(null, Typeface.BOLD)
        button.isAllCaps = false
        button.background = backgroundDrawable
        button.gravity = Gravity.CENTER
        button.setPadding(2,2,2,2)
        return button
    }

    fun getRoundedBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val inpBitmap = bitmap
        var width = 0
        var height = 0
        width = inpBitmap.width
        height = inpBitmap.height
        if (width <= height) {
            height = width
        } else {
            width = height
        }
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val roundPx = pixels.toFloat()
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        //paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(inpBitmap, rect, rect, paint)
        return output
    }

    fun createTextIconName(
        context: Context,
        iconName: String,
        shadowColor: Int,
        textColor: Int,
        marginStart: Int
    ): TextView {
        val textIconName = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.START or Gravity.BOTTOM
        )
        layoutParams.setMargins(marginStart.toPx(context), 0, 0, 0)
        textIconName.layoutParams = layoutParams
        textIconName.text = iconName
        textIconName.setTextColor(textColor)
        textIconName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        textIconName.setTypeface(null, Typeface.BOLD)
        textIconName.setShadowLayer(5F, 2F, 2F, shadowColor)
        textIconName.isAllCaps = false
        textIconName.updateLayoutParams {
            width = 100.toPx(context)
            height = 55.toPx(context)
        }
        textIconName.gravity = Gravity.CENTER_VERTICAL
        return textIconName
    }

    fun createTextHeader(
        context: Context,
        iconName: String,
        shadowColor: Int,
        textColor: Int,
        marginStart: Int
    ): TextView {
        val textIconName = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.START or Gravity.BOTTOM
        )
        layoutParams.setMargins(marginStart.toPx(context), 0, 0, 0)
        textIconName.layoutParams = layoutParams
        textIconName.text = iconName
        textIconName.setTextColor(textColor)
        textIconName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
        textIconName.setTypeface(null, Typeface.BOLD)
        textIconName.setShadowLayer(5F, 2F, 2F, shadowColor)
        textIconName.isAllCaps = false
        textIconName.updateLayoutParams {
            width = FrameLayout.LayoutParams.WRAP_CONTENT
            height = FrameLayout.LayoutParams.WRAP_CONTENT
        }
        textIconName.gravity = Gravity.TOP
        return textIconName
    }

    fun createTextMessage(
        context: Context,
        payload: String,
        textColor: Int,
        marginStart: Int
    ): TextView {
        val textMessage = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(marginStart.toPx(context), 0, 0, 0)
        textMessage.layoutParams = layoutParams
        textMessage.text = payload
        textMessage.setTextColor(textColor)
        textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
        textMessage.setTypeface(null, Typeface.NORMAL)
        textMessage.isAllCaps = false
        textMessage.updateLayoutParams {
            width = FrameLayout.LayoutParams.WRAP_CONTENT
            height = FrameLayout.LayoutParams.WRAP_CONTENT
        }
        return textMessage
    }

      private fun addMessageRow(
        messageRow: LinearLayout,
        imageUserBitmap: Bitmap?,
        userName: String,
        messagePayload: String,
        messageCreateTime: String,
        context: Context
    ) {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.START or Gravity.BOTTOM
        )
        layoutParams.setMargins(0, 50, 0, 0)
        messageRow.layoutParams = layoutParams

        val userIcon = createUserIcon(
            context= context,
            drawable = context.resources.getDrawable(R.drawable.empty_profile, null),
            backgroundDrawable = context.resources.getDrawable(R.drawable.button_round_while, null),
            imageUserBitmap = imageUserBitmap
        )

        val nameIcon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createTextHeader(
                context = context,
                iconName = userName,
                shadowColor = context.resources.getColor(R.color.black, null),
                textColor = context.resources.getColor(R.color.textColorWhite, null),
                marginStart = 20
            )
        } else {
            createTextHeader(
                context = context,
                iconName = userName,
                shadowColor = ContextCompat.getColor(context, R.color.black),
                textColor = ContextCompat.getColor(context, R.color.textColorWhite),
                marginStart = 20
            )
        }

        val messageText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createTextMessage(
                context = context,
                payload = messagePayload,
                textColor = context.resources.getColor(R.color.white, null),
                marginStart = 20
            )
        } else {
            createTextMessage(
                context = context,
                payload = messagePayload,
                textColor = ContextCompat.getColor(context, R.color.white),
                marginStart = 20
            )
        }

        val createTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createTextMessage(
                context = context,
                payload = messageCreateTime,
                textColor = context.resources.getColor(R.color.white, null),
                marginStart = 20
            )
        } else {
            createTextMessage(
                context = context,
                payload = messageCreateTime,
                textColor = ContextCompat.getColor(context, R.color.white),
                marginStart = 20
            )
        }

        val messageTextRow = createNewVerticalLinearLayout(context)
        messageTextRow.addView(nameIcon)
        messageTextRow.addView(createTime)
        messageTextRow.addView(messageText)

        messageRow.addView(userIcon)
        messageRow.addView(messageTextRow)
        messageRow.addView(createNewHorizontalLinearLayout(context))
    }

    fun getFileFromUri(context: Context, uri: Uri?): File? {
        uri ?: return null
        uri.path ?: return null

        var newUriString = uri.toString()
        newUriString = newUriString.replace(
            "content://com.android.providers.downloads.documents/",
            "content://com.android.providers.media.documents/"
        )
        newUriString = newUriString.replace(
            "/msf%3A", "/image%3A"
        )
        val newUri = Uri.parse(newUriString)

        var realPath = String()
        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (newUri.path?.contains("/document/image:") == true) {
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(newUri).split(":")[1])
        } else {
            databaseUri = newUri
            selection = null
            selectionArgs = null
        }
        try {
            val column = "_data"
            val projection = arrayOf(column)
            val cursor = context.contentResolver.query(
                databaseUri,
                projection,
                selection,
                selectionArgs,
                null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(column)
                    realPath = cursor.getString(columnIndex)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("GetFileUri Exception:", e.message ?: "")
        }
        val path = realPath.ifEmpty {
            when {
                newUri.path?.contains("/document/raw:") == true -> newUri.path?.replace(
                    "/document/raw:",
                    ""
                )
                newUri.path?.contains("/document/primary:") == true -> newUri.path?.replace(
                    "/document/primary:",
                    "/storage/emulated/0/"
                )
                else -> return null
            }
        }
        return if (path.isNullOrEmpty()) null else File(path)
    }
}