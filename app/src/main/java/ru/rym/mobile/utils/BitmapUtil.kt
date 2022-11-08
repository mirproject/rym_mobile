package ru.rym.mobile.utils

import android.graphics.*

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.resize(maxLength: Int): Bitmap {
    return try {
       // if (this.height >= this.width) {
       //     if (this.height <= maxLength) { // if image already smaller than the required height
       //         return this
        //    }
        //    val aspectRatio = this.width.toDouble() / this.height.toDouble()
        //    val targetWidth = (maxLength * aspectRatio).toInt()
        //    Bitmap.createScaledBitmap(this, targetWidth, maxLength, false)
        //} else {
            if (this.width <= maxLength) { // if image already smaller than the required height
                return this
            }
            val aspectRatio = this.height.toDouble() / this.width.toDouble()
            val targetHeight = (maxLength * aspectRatio).toInt()
            Bitmap.createScaledBitmap(this, maxLength, targetHeight, false)
       // }
    } catch (e: Exception) {
        this
    }
}

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    return try {
        Bitmap.createScaledBitmap(this, maxWidth, maxHeight, false)
    } catch (e: Exception) {
        this
    }
}

fun Bitmap.resizeMax(maxWidth: Int, maxHeight: Int): Bitmap {
    return try {
        if (this.width <= maxWidth && this.height <= maxHeight) {
            return this
        }
        Bitmap.createScaledBitmap(this, maxWidth, maxHeight, false)
    } catch (e: Exception) {
        this
    }
}

fun Bitmap.fill(maxLength: Int): Bitmap {
    return try {
        val aspectRatio = this.height.toDouble() / this.width.toDouble()
        val targetHeight = (maxLength * aspectRatio).toInt()
        Bitmap.createScaledBitmap(this, maxLength, targetHeight, false)
    } catch (e: Exception) {
        this
    }
}