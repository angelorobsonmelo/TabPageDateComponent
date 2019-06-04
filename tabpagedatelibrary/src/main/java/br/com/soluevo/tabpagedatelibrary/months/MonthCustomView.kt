package br.com.soluevo.tabpagedatelibrary.months

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import br.com.soluevo.tabpagedatelibrary.R
import br.com.soluevo.tabpagedatelibrary.commom.ViewModelFactory
import br.com.soluevo.tabpagedatelibrary.commom.di.ContextModule
import br.com.soluevo.tabpagedatelibrary.databinding.MonthComponentBinding
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import br.com.soluevo.tabpagedatelibrary.months.di.component.DaggerMonthComponent
import br.com.soluevo.tabpagedatelibrary.months.handler.MonthHandler
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject
import androidx.core.os.HandlerCompat.postDelayed
import android.os.Handler


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

    fun getMonths(cookieId: String, activity: AppCompatActivity, fm: FragmentManager) {
        var tabs: TabLayout? = null
        viewModel = ViewModelProviders.of(activity, viewModelFactory)[MonthViewModel::class.java]

        viewModel?.getMonths(cookieId)

        binding.lifecycleOwner = activity
        binding.viewModel = viewModel

        viewModel?.successObserver?.observe(activity, Observer {
            months.clear()
            months.addAll(it)

            val sectionsPagerAdapter = SectionsPagerAdapter(it, fm)
            val viewPager: ViewPager = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            tabs = findViewById(R.id.tabs)
            tabs?.setupWithViewPager(viewPager)

            val lastItem = it.size - 1
            tabs?.getTabAt(lastItem)?.customView?.isSelected = true

            Handler().postDelayed({
                tabs?.setScrollPosition(lastItem, 0f, true)
                viewPager.currentItem = lastItem
                // or
                // tabLayout.getTabAt(index).select();
            }, 100)

            tabs?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel?.setIndex(tab.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    // called when tab unselected
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    // called when a tab is reselected
                }
            })

        })


        viewModel?.errorObserver?.observe(activity, Observer {

        })

        viewModel?.position?.observe(activity, Observer<Int> {
            handler?.setMonth(months[it])
        })
    }


    fun clearDisposable() {
        viewModel?.disposables?.clear()
    }
}