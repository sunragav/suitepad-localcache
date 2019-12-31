package com.sunragav.suitepad.fileprovider

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class FileProviderActivity : Activity() {
    companion object {
        private const val APPLICATION_ID = "com.sunragav.suitepad.proxyserver"
        private const val CLASSNAME = "com.sunragav.suitepad.proxyserver.ProxyWebServer"
        const val GET_URI_ACTION = "com.sunragav.suitepad.GetURIAction"
    }

    private val runnable = Runnable {

        val clipData = ClipData(
            ClipDescription(
                "Meshes",
                arrayOf(ClipDescription.MIMETYPE_TEXT_URILIST)
            ), ClipData.Item(
                shareFile("sample.html")
            )
        ).apply {
            addItem(
                ClipData.Item(
                    shareFile("sample.json")
                )
            )
        }

        val intent =
            Intent().also {
                it.setClassName(APPLICATION_ID, CLASSNAME)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                it.clipData = clipData
                it.action = GET_URI_ACTION
            }
        startService(intent)
        finish()
    }

    private fun shareFile(fileName: String): Uri {
        val file =
            File(filesDir.path.toString() + "/" + fileName)
        println("sharing ${file.absolutePath}")
        if (!file.exists()) { // json isn't in the files dir, copy from the res/raw
            file.createNewFile()
        }

    return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file)
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    println("File provider activity started!!")
    Thread(runnable).start()
}

private fun writeFile(file: File, content: String): Boolean {
    var stream: FileOutputStream? = null
    try {
        if (!file.exists()) {
            val created = file.createNewFile()
            if (!created) {
                return false
            }
        }
        stream = FileOutputStream(file)
        stream.write(content.toByteArray())
        stream.flush()
        stream.close()
        return true
    } catch (e: IOException) {
        Log.e("provider", "IOException writing file: ", e)
    } finally {
        try {
            if (stream != null) {
                stream.close()
            }
        } catch (e: IOException) {
            Log.e("provider", "IOException closing stream: ", e)
        }
    }
    return false
}
}




