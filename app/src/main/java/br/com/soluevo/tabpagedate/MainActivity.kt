package br.com.soluevo.tabpagedate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.soluevo.tabpagedate.fragments.OneFragment
import br.com.soluevo.tabpagedate.fragments.OtherFragment
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import br.com.soluevo.tabpagedatelibrary.months.MonthCustomView
import br.com.soluevo.tabpagedatelibrary.months.handler.MonthHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MonthHandler {

    private lateinit var customView: MonthCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpCustomTabDate()
    }

    private fun setUpCustomTabDate() {
        customView = month_custom_view_id
        customView.handler = this
        customView.getMonths(
            "_session_id=x0LzXZpp1OnRfed0cBbNYhetZjs; path=/; HttpOnly",
            this,
            supportFragmentManager
        )
    }

    override fun setMonth(monthResponse: MonthResponse) {

    }

    override fun setMonsths(months: MutableList<MonthResponse>) {
        months.forEach { monthResponse ->
            val title =
                customView.getTitle(monthResponse)

            if (customView.isToday(monthResponse)) {
                customView.sectionsPagerAdapter.addFragment(OneFragment(), title)
            } else {
                customView.sectionsPagerAdapter.addFragment(OtherFragment(), title)
            }
        }

        customView.sectionsPagerAdapter.notifyDataSetChanged()
    }

    override fun setError(error: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        customView.clearDisposable()
    }
}