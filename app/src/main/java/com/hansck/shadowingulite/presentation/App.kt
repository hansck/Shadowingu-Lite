package com.hansck.shadowingulite.presentation

import android.app.Application
import com.hansck.shadowingulite.database.AppDatabase


/**
 * Created by Hans CK on 31-Oct-17.
 */
class App : Application() {

    var connected = false

    companion object {
        var instance: App? = null
        var database: AppDatabase? = null

        val getInstance: App
            get() {
                if (instance == null) {
                    synchronized(App::class.java) {
                        if (instance == null) {
                            instance = App()
                        }
                    }
                }
                return instance!!
            }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getInstance(this)
    }
}