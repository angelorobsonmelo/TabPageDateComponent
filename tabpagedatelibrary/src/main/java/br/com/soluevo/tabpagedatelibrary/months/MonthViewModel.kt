package br.com.soluevo.tabpagedatelibrary.months

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.soluevo.tabpagedatelibrary.commom.BaseViewModel
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import br.com.soluevo.tabpagedatelibrary.service.months.MonthsApiDataSource
import br.com.soluevo.tabpagedatelibrary.utils.DateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MonthViewModel @Inject constructor(
    private val monthsApiDataSource: MonthsApiDataSource
) : BaseViewModel<MutableList<MonthResponse>>() {

    val disposables = CompositeDisposable()
    private val _index = MutableLiveData<Int>()

    val position: LiveData<Int> = Transformations.map(_index) {
        it
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getMonths(cookieId: String) {
        val disposable = monthsApiDataSource.getMonths(cookieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.reverse()
                DateUtil.fillList(it)
            }
            .doOnSubscribe { isLoadingObserver.value = true }
            .doAfterTerminate { isLoadingObserver.value = false }
            .subscribe(
                {
                    successObserver.value = DateUtil.fillList(it)
                },
                { throwable ->
                    errorObserver.value = throwable.localizedMessage
                }
            )

        disposables.add(disposable)
    }
}