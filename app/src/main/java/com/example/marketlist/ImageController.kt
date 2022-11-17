package com.example.marketlist

import android.content.Context
import android.net.Uri
import java.io.File

object ImageController {
    fun saveImage(context: Context, id:Long, uri: Uri){
        val file = File(context.filesDir, id.toString())
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!
        file.writeBytes(bytes)
    }

    fun getPhotoUri(context: Context, id: Long):Uri{
        val file = File(context.filesDir,id.toString())
        return if (file.exists()) Uri.fromFile(file)
        else Uri.parse("android.resourse://com.example.marketlist/drawable/listinlogo3.png")
    }

    fun deleteImage(context: Context, id:Long){
        val file = File(context.filesDir, id.toString())
        file.delete()
    }
}