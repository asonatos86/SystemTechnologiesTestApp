package com.bobko.systemtechnologiestestapp.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.bobko.systemtechnologiestestapp.model.ApiResponse
import com.bobko.systemtechnologiestestapp.model.Currency
import com.bobko.systemtechnologiestestapp.net.CurrencyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyModelDataSource internal constructor(var currencyApi: CurrencyApi?, var progressBarLoader: MutableLiveData<Boolean>) : PositionalDataSource<Currency?>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Currency?>) {
        progressBarLoader.postValue(true)
        currencyApi?.getCurrencyList(params.startPosition, params.loadSize)?.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                progressBarLoader.postValue(false)
                Log.d("RetroRepository","Failed:::"+t.message)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                progressBarLoader.postValue(false)
                response.body()?.data?.let { callback.onResult(it) }
            }
        })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Currency?>) {
        progressBarLoader.postValue(true)
        currencyApi?.getCurrencyList(params.requestedStartPosition, params.pageSize)?.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                progressBarLoader.postValue(false)
                Log.d("RetroRepository","Failed:::"+t.message)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                progressBarLoader.postValue(false)
                response.body()?.data?.let { callback.onResult(it,0) }
            }
        })
    }
}