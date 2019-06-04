package br.com.soluevo.tabpagedate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse
import br.com.soluevo.tabpagedatelibrary.months.handler.MonthHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MonthHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = month_custom_view_id
        customView.handler = this
        customView.getMonths("_session_id=x0LzXZpp1OnRfed0cBbNYhetZjs; path=/; HttpOnly", this, supportFragmentManager)
    }

    override fun setMonth(monthResponse: MonthResponse) {

    }

    override fun setError(error: String) {

    }
}