package com.bobko.systemtechnologiestestapp

import android.app.Application

class CurrencyApplication : Application() {
    private var repository: Repository? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = Repository(this)
    }

    fun getRepository(): Repository? {
        return repository
    }

    fun getInstance(): Application? {
        return instance
    }

    companion object {
        var instance: CurrencyApplication? = null
    }
}