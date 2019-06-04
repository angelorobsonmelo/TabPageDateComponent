package br.com.soluevo.tabpagedatelibrary.months

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import java.text.DateFormatSymbols
import java.util.*

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val monthResponse: MutableList<MonthResponse>, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val shortMonths = DateFormatSymbols().shortMonths

    var title = ""

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val mCalendar = Calendar.getInstance()
        val mTodayDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        val mTodayMonth = mCalendar.get(Calendar.MONTH)
        val mTodayYear = mCalendar.get(Calendar.YEAR)

        title = if (monthResponse[position].month == mTodayMonth + 1 && monthResponse[position].year == mTodayYear) {
            "Hoje"
        } else {
            "${shortMonths[monthResponse[position].month - 1]} / ${monthResponse[position].year}"
        }

        return title
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return monthResponse.size
    }

}