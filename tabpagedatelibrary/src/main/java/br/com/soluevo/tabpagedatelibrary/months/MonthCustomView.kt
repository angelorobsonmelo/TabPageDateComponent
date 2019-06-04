package br.com.soluevo.tabpagedatelibrary.months

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.soluevo.tabpagedatelibrary.R
import br.com.soluevo.tabpagedatelibrary.commom.ViewModelFactory
import br.com.soluevo.tabpagedatelibrary.commom.di.ContextModule
import br.com.soluevo.tabpagedatelibrary.databinding.MonthComponentBinding
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import br.com.soluevo.tabpagedatelibrary.months.di.component.DaggerMonthComponent
import br.com.soluevo.tabpagedatelibrary.months.handler.MonthHandler
import javax.inject.Inject

class MonthCustomView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var handler: MonthHandler? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: MonthComponentBinding
    private var viewModel: MonthViewModel? = null
    private val months = mutableListOf<MonthResponse>()

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.month_component, this, true
        )

        setUpElements()
    }

    private fun setUpElements() {
        injectDependencies()
    }

    private fun injectDependencies() {
        DaggerMonthComponent.builder()
            .contextModule(ContextModule(context))
            .build()
            .inject(this)
    }

    fun getMonths(cookieId: String, activity: AppCompatActivity) {
        viewModel = ViewModelProviders.of(activity, viewModelFactory)[MonthViewModel::class.java]
        viewModel?.getMonths(cookieId)

        binding.lifecycleOwner = activity
        binding.viewModel = viewModel

        viewModel?.successObserver?.observe(activity, Observer {

        })

        viewModel?.errorObserver?.observe(activity, Observer {

        })
    }

    fun clearDisposable() {
        viewModel?.disposables?.clear()
    }
}