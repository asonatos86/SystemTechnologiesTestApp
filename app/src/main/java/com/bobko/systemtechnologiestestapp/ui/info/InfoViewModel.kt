package com.bobko.systemtechnologiestestapp.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bobko.systemtechnologiestestapp.CurrencyApplication
import com.bobko.systemtechnologiestestapp.Repository
import com.bobko.systemtechnologiestestapp.model.Currency
import com.bobko.systemtechnologiestestapp.net.CurrencyApi

class InfoViewModel : ViewModel() {
    private val repository: Repository
    val backClick = MutableLiveData<Boolean>()

    init {
        repository = CurrencyApplication.instance?.getRepository()!!
    }

    fun getById(id: String): MutableLiveData<Currency>
    {
        return repository.getById(id)
    }
    fun backClick()
    {
        backClick.value = true
    }
}