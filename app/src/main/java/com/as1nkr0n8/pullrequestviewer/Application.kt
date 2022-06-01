package com.as1nkr0n8.pullrequestviewer

import android.app.Application
import com.as1nkr0n8.data.network.RetrofitClient

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.buildInstance(BuildConfig.REPO_URL)
    }
}