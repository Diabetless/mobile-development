package com.ch2Ps073.diabetless.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ch2Ps073.diabetless.R
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

fun getBitmapFromUri(context: Context, uri: Uri?): Bitmap? {
    return try {
        val dummy = ImageView(context)
        dummy.setImageURI(uri)
        val drawable = dummy.drawable as BitmapDrawable
        drawable.bitmap
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

fun cropImage(bitmap: Bitmap, containerImage: View, containerOverlay: View): ByteArray {
    val heightOriginal = containerImage.height
    val widthOriginal = containerImage.width
    val heightFrame = containerOverlay.height
    val widthFrame = containerOverlay.width
    val leftFrame = containerOverlay.left
    val topFrame = containerOverlay.top
    val heightReal = bitmap.height
    val widthReal = bitmap.width
    val widthFinal = widthFrame * widthReal / widthOriginal
    val heightFinal = heightFrame * heightReal / heightOriginal
    val leftFinal = leftFrame * widthReal / widthOriginal
    val topFinal = topFrame * heightReal / heightOriginal
    val bitmapFinal = Bitmap.createBitmap(
        bitmap,
        leftFinal, topFinal, widthFinal, heightFinal
    )
    val stream = ByteArrayOutputStream()
    bitmapFinal.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        stream
    ) //100 is the best quality possibe
    return stream.toByteArray()
}

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(columnIndex)
    } catch (e: java.lang.Exception) {
        Log.e("getRealPathFromURI", "getRealPathFromURI Exception : $e")
        ""
    } finally {
        cursor?.close()
    }
}

fun getBitmapFromFile(context: Context?, fileName: String?): Bitmap? {
    return if (fileName.isNullOrEmpty()) null else try {
        val fis = FileInputStream(fileName)
        BitmapFactory.decodeStream(fis)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

fun ImageView.setImageFromUrl(url: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)

    Glide.with(this)
        .load(url ?: "")
        .apply(options)
        .into(this)
}