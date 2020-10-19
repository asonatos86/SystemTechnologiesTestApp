package com.bobko.systemtechnologiestestapp.ui.converter

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bobko.systemtechnologiestestapp.CurrencyApplication
import com.bobko.systemtechnologiestestapp.Repository
import com.bobko.systemtechnologiestestapp.model.Currency

class ConvertViewModel : ViewModel() {
    var currencyFrom = ObservableField<Currency>()
    var currencyTo = ObservableField<Currency>()

    val selectCurrencyFromTrigger = MutableLiveData<Boolean>()
    val selectCurrencyToTrigger = MutableLiveData<Boolean>()
    var currencyList = MutableLiveData<List<Currency>>()
    var price = ObservableField<String>()
    var resultPrice = ObservableField<String>()
    var asd = MutableLiveData<String>()
    private val repository: Repository

    init {
        repository = CurrencyApplication.instance?.getRepository()!!
        loadCurrencyList()
    }

    fun selectCurrencyFrom()
    {
        selectCurrencyFromTrigger.value = true
    }
    fun selectCurrencyTo()
    {
        selectCurrencyToTrigger.value = true
    }
    private fun loadCurrencyList()
    {
        currencyList = repository.getList(0,10)
    }
    fun getNameList(): List<String>
    {
        return repository.getCurrencyNames(currencyList.value!!)
    }
    fun loadPrice()
    {
        price.set(String.format("%.4f", currencyFrom?.get()?.priceUsd?.toDouble()?.div(currencyTo?.get()?.priceUsd?.toDouble()!!)))
    }
    fun loadDefault()
    {
        currencyFrom.set(currencyList.value?.get(0))
        currencyTo.set(currencyList.value?.get(1))
        loadPrice()
    }
    fun textChange(text: CharSequence)
    {
        resultPrice.set(String.format("%.4f", (price?.get()?.toDouble())?.times(text.toString().toDouble())))
    }
    fun switchCurrency()
    {
        var buffer = currencyFrom.get()
        currencyFrom.set(currencyTo.get())
        currencyTo.set(buffer)
        loadPrice()
    }
}