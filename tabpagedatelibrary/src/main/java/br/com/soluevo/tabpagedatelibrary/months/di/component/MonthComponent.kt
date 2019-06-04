package br.com.soluevo.tabpagedatelibrary.months.di.component

import br.com.soluevo.tabpagedatelibrary.commom.di.ContextModule
import br.com.soluevo.tabpagedatelibrary.months.MonthCustomView
import br.com.soluevo.tabpagedatelibrary.months.di.module.MonthModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MonthModule::class, ContextModule::class])
interface MonthComponent {

    fun inject(monthCustomView: MonthCustomView)
}