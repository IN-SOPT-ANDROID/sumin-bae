package org.sopt.sample

import android.app.Application
import org.sopt.sample.data.local.PreferenceUtil

class SeminarApp : Application() {

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}