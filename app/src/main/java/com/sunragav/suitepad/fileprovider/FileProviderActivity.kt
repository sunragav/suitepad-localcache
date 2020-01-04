package com.sunragav.suitepad.fileprovider

import android.app.Activity
import android.os.Build
import android.os.Bundle
import com.sunragav.suitepad.fileprovider.BuildConfig.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FileProviderActivity : Activity() {


    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent.action) {
            PROXY_SERVER_GET_URI_ACTION -> {
                provideUrisTask(PROXY_SERVER_APPLICATION_ID, PROXY_SERVER_CLASSNAME)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        startService(it)
                        finish()
                    }
                    .doOnSubscribe { disposable.add(it) }
                    .subscribe()
            }
        }
    }

    override fun finish() {
        disposable.dispose()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finishAndRemoveTask()
        } else {
            super.finish()
        }
    }

}




