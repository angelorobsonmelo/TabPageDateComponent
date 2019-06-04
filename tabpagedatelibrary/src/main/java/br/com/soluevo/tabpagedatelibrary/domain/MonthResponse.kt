package br.com.soluevo.tabpagedatelibrary.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonthResponse(
    var id: String,
    var month: Int,
    var year: Int
) : Parcelable {
    constructor(month: Int, year: Int) : this("", month, year)
}