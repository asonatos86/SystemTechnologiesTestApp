package com.bobko.systemtechnologiestestapp.net

import com.bobko.systemtechnologiestestapp.model.ApiResponse
import com.bobko.systemtechnologiestestapp.model.SingleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {
    @GET("assets")
    open fun getCurrencyList(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<ApiResponse>

    @GET("assets/{id}")
    open fun getById(@Path("id") id: String): Call<SingleResponse>
}