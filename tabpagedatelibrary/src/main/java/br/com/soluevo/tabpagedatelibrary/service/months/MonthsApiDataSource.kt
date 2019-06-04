package br.com.soluevo.tabpagedatelibrary.service.months

import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MonthsApiDataSource {

    @GET("account/getMonths")
    fun getMonths(@Header("Cookie") id: String): Observable<MutableList<MonthResponse>>
}