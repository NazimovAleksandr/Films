package com.nazimovaleksandr.films.single_activity

import android.app.Application
import com.nazimovaleksandr.films.single_activity.data.DataManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DataManager.init(applicationContext)
    }
}