package br.com.soluevo.tabpagedatelibrary.months

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject


class MonthCustomView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var handler: MonthHandler? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: MonthComponentBinding
    private var viewModel: MonthViewModel? = null
    private val months = mutableListOf<MonthResponse>()
    var sectionsPagerAdapter = SectionsPagerAdapter()

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

    fun getMonthsFromActivity(
        cookieId: String,
        activity: AppCompatActivity,
        fm: FragmentManager
    ) {
        viewModel = ViewModelProviders.of(activity, viewModelFactory)[MonthViewModel::class.java]
        viewModel?.getMonths(cookieId)

        binding.lifecycleOwner = activity
        binding.viewModel = viewModel

        viewModel?.successObserver?.observe(activity, Observer {
            months.clear()
            months.addAll(it)

            val pair = setUpTabsAndViewPager(fm)
            val viewPager = pair.first
            val tabs = pair.second

            handler?.setMonsths(it)

            val lastItem = it.size - 1

            navigateToTabToday(tabs, lastItem, viewPager)
            initTabOnClickListener(tabs)
        })

        viewModel?.errorObserver?.observe(activity, Observer {

        })

        viewModel?.position?.observe(activity, Observer<Int> {
            handler?.setMonth(months[it])
        })
    }

    fun getMonthsFromFragment(
        cookieId: String,
        fragment: Fragment,
        fm: FragmentManager
    ) {
        viewModel = ViewModelProviders.of(fragment, viewModelFactory)[MonthViewModel::class.java]
        viewModel?.getMonths(cookieId)

        binding.lifecycleOwner = fragment
        binding.viewModel = viewModel

        viewModel?.successObserver?.observe(fragment, Observer {
            months.clear()
            months.addAll(it)

            val pair = setUpTabsAndViewPager(fm)
            val viewPager = pair.first
            val tabs = pair.second

            handler?.setMonsths(it)

            val lastItem = it.size - 1

            navigateToTabToday(tabs, lastItem, viewPager)
            initTabOnClickListener(tabs)
        })

        viewModel?.errorObserver?.observe(fragment, Observer {

        })

        viewModel?.position?.observe(fragment, Observer<Int> {
            handler?.setMonth(months[it])
        })
    }

    private fun setUpTabsAndViewPager(
        fm: FragmentManager
        ): Pair<ViewPager, TabLayout?> {
        sectionsPagerAdapter = SectionsPagerAdapter(fm)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        tabs.setupWithViewPager(viewPager)
        return Pair(viewPager, tabs)
    }

    private fun navigateToTabToday(
        tabs: TabLayout?,
        lastItem: Int,
        viewPager: ViewPager
    ) {
        tabs?.getTabAt(lastItem)?.customView?.isSelected = true

        Handler().postDelayed({
            tabs?.setScrollPosition(lastItem, 0f, true)
            viewPager.currentItem = lastItem
            // or
            // tabLayout.getTabAt(index).select();
        }, 100)
    }

    private fun initTabOnClickListener(tabs: TabLayout?) {
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
    }

    fun getTitle(
        monthResponse: MonthResponse
    ): String {
        val shortMonths = DateFormatSymbols().shortMonths
        return if (isToday(monthResponse)) {
            "Hoje"
        } else {
            "${shortMonths[monthResponse.month - 1]} / ${monthResponse.year}"
        }
    }

    fun isToday(
        monthResponse: MonthResponse
    ): Boolean {
        val mCalendar = Calendar.getInstance()
        val mTodayMonth = mCalendar.get(Calendar.MONTH)
        val mTodayYear = mCalendar.get(Calendar.YEAR)

        return monthResponse.month == mTodayMonth + 1 && monthResponse.year == mTodayYear
    }

    fun clearDisposable() {
        viewModel?.disposables?.clear()
    }
}