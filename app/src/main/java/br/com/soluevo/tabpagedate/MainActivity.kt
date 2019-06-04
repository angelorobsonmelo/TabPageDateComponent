package br.com.soluevo.tabpagedate

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.soluevo.tabpagedate.ui.main.SectionsPagerAdapter
import br.com.soluevo.tabpagedatelibrary.months.MonthCustomView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = month_custom_view_id
        customView.getMonths("_session_id=x0LzXZpp1OnRfed0cBbNYhetZjs; path=/; HttpOnly", this, supportFragmentManager)
    }
}