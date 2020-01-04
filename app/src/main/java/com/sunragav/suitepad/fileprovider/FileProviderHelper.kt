package com.sunragav.suitepad.fileprovider

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import io.reactivex.Single
import java.io.File

 fun Activity.provideUrisTask(applicationId: String, componentClassName: String): Single<Intent> =
    Single.fromCallable {
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

        Intent().also {
            it.setClassName(applicationId, componentClassName)
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            it.clipData = clipData
            it.action = BuildConfig.PROXY_SERVER_GET_URI_ACTION
        }
    }

 fun Activity.shareFile(fileName: String): Uri {
    val file =
        File("${filesDir.path}/$fileName")
    if (!file.exists()) { // when file is not there create an empty file so that the uri could be shared
        file.createNewFile()
    }

    return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file)
}