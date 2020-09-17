package com.bobko.systemtechnologiestestapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bobko.systemtechnologiestestapp.CurrencyApplication
import com.bobko.systemtechnologiestestapp.Repository
import com.bobko.systemtechnologiestestapp.model.ApiResponse
import com.bobko.systemtechnologiestestapp.model.Currency
import com.bobko.systemtechnologiestestapp.net.CurrencyApi

class CurrencyViewModel(application: Application): AndroidViewModel(
    application
) {
    private val currencyApi: CurrencyApi?
    val showProgress = MutableLiveData<Boolean>()
    val showDetails = MutableLiveData<Boolean>()
    var targetCurrencyModel :Currency? = null
    fun getCurrencyApi(): CurrencyApi?
    {
        return currencyApi
    }

    init {
        currencyApi = CurrencyApplication.instance?.getRepository()?.getCurrencyApi()
    }

}