package br.com.soluevo.tabpagedatelibrary.commom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T>: ViewModel() {

    val successObserver   = MutableLiveData<T>()
    var emptyObserver     = MutableLiveData<Boolean>()
    val errorObserver     = MutableLiveData<String>()
    val isLoadingObserver = MutableLiveData<Boolean>()

}