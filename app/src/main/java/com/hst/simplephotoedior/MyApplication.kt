package com.hst.simplephotoedior

import android.app.Application
import com.hst.simplephotoedior.Utils.Preference

class MyApplication : Application() {
    companion object {
        private var preference: Preference? = null
        private var application: MyApplication? = null

        @JvmStatic
        fun getInstance(): Preference? {
            if (preference == null) {
                preference = Preference.buildInstance(application)
            }
            preference?.isOpenFirst
            return preference
        }
    }


    override fun onCreate() {
        super.onCreate()
        application = this
    }
}