package com.engineerskasa.giphy

import android.app.Application
import com.engineerskasa.giphy.data.database.TrendingDatabase

class GiphyApplication : Application() {
    companion object {
        lateinit var instance: GiphyApplication
        lateinit var database: TrendingDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = TrendingDatabase.invoke(this)
    }
}