package br.com.soluevo.tabpagedatelibrary.months.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.soluevo.tabpagedatelibrary.commom.ViewModelFactory
import br.com.soluevo.tabpagedatelibrary.commom.ViewModelKey
import br.com.soluevo.tabpagedatelibrary.months.MonthViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MonthViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MonthViewModel::class)
    internal abstract fun monthViewModel(monthViewModel: MonthViewModel): ViewModel

}