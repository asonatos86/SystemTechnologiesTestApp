package com.bobko.systemtechnologiestestapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bobko.systemtechnologiestestapp.model.ApiResponse
import com.bobko.systemtechnologiestestapp.model.Currency
import com.bobko.systemtechnologiestestapp.model.SingleResponse
import com.bobko.systemtechnologiestestapp.net.CurrencyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository(context: Context?){
    private val retrofit: Retrofit?
    private val currencyApi: CurrencyApi?
    private val currencyResponse = MutableLiveData<Currency>()
    private val currencyList = MutableLiveData<List<Currency>>()

    fun getCurrencyApi(): CurrencyApi? {
        return currencyApi
    }

    fun getById(id: String): MutableLiveData<Currency>
    {
        currencyApi?.getById(id)?.enqueue(object : Callback<SingleResponse> {
            override fun onFailure(call: Call<SingleResponse>, t: Throwable) {
                Log.d("RetroRepository","Failed:::"+t.message)
            }

            override fun onResponse(call: Call<SingleResponse>, response: Response<SingleResponse>) {
                currencyResponse.value = response.body()?.data
            }
        })
        return currencyResponse
    }

    fun getList(offset: Int,limit: Int) : MutableLiveData<List<Currency>>
    {
        currencyApi?.getCurrencyList(offset, limit)?.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("RetroRepository","Failed:::"+t.message)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                currencyList.value = response.body()?.data
            }
        })
        return currencyList
    }

    fun getCurrencyNames(list: List<Currency>): List<String>
    {
        var resultList = mutableListOf<String>()
        for (i in list)
        {
            resultList.add(i.id)
        }
        return resultList
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(context?.getResources()?.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        currencyApi = retrofit.create(CurrencyApi::class.java)
    }
}