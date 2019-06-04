# Tab Page Date Component

# Usage

Add in your build.gradle.

```
dependencies {
 
    // Tabs date
    implementation 'com.github.angelorobsonmelo:TabPageDateComponent:1.0.1'
}

```


In your build.gradle.
```
dependencies {
 
    // Tabs date
    implementation 'com.github.angelorobsonmelo:TabPageDateComponent:1.0.1'
}

```

Include the component in your layout

```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        tools:context=".MainActivity">

        <br.com.soluevo.tabpagedatelibrary.months.MonthCustomView
                android:id="@+id/month_custom_view_id"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content" />

</LinearLayout>

```

In your onCreate (or onCreateView for a fragment) method, bind the id defined in the component and implement the MonthHandler interface
For exemple:

```Java

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
            "cookie id",
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
                customView.sectionsPagerAdapter.addFragment(TodayFragment(), title)
            } else {
                customView.sectionsPagerAdapter.addFragment(OthersFragment(), title)
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

```

The setMonth function is triggered by clicking a tab

```java

 override fun setMonth(monthResponse: MonthResponse) {
      // get month response from the clicked tab
    }

```

the setMonsths function is triggered after the request for api

```java

 override fun setMonsths(months: MutableList<MonthResponse>) { 
  // get all months loaded from api
 }

```

the setError function is fired after api encounters an error

```java

 override fun setError(error: String) { 
  // get error
 }

```

