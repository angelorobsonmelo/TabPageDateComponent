package br.com.soluevo.tabpagedatelibrary.months.di.module

import br.com.soluevo.tabpagedatelibrary.commom.di.NetWorkModule
import br.com.soluevo.tabpagedatelibrary.service.months.MonthsApiDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetWorkModule::class, MonthViewModelModule::class])
class MonthModule {

    @Provides
    @Singleton
    fun provideMonthsApiDataSource(retrofit: Retrofit): MonthsApiDataSource =
        retrofit.create(MonthsApiDataSource::class.java)
}