package br.com.soluevo.tabpagedatelibrary.months.handler

import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse

interface MonthHandler {

    fun setMonth(monthResponse: MonthResponse)
    fun setError(error: String)
    fun setMonsths(months: MutableList<MonthResponse>)
}