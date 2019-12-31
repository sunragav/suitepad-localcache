package com.sunragav.suitepad.fileprovider

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.FileProvider
import com.sunragav.suitepad.fileprovider.BuildConfig.*
import java.io.File


class FileProviderActivity : Activity() {


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
                it.setClassName(PROXY_SERVER_APPLICATION_ID, PROXY_SERVER_CLASSNAME)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                it.clipData = clipData
                it.action = PROXY_SERVER_GET_URI_ACTION
            }
        startService(intent)
        finish()
    }

    private fun shareFile(fileName: String): Uri {
        val file =
            File(filesDir.path.toString() + "/" + fileName)
        println("sharing ${file.absolutePath}")
        if (!file.exists()) { // when file is not there create an empty file so that the uri could be shared
            file.createNewFile()
        }

        return FileProvider.getUriForFile(this, APPLICATION_ID, file)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("File provider activity started!!")
        Thread(runnable).start()
    }

}




