# Tab Page Date Component
[![Maven Central][maven_central_badge_svg]][maven_central_badge_app] [![Android Arsenal][android_arsenal_badge_svg]][android_arsenal_badge_link] [![Android Weekly][android_weekly_badge_svg]][android_weekly_badge_link]

![icon][demo_icon]

A custom ViewPager title strip which gives continuous feedback to the user when scrolling.

This library has been added some features and utilities based on [android-SlidingTabBasic][google_slidingtabbasic] project of Google Samples.


![SmartTabLayout Demo1][demo1_gif] ![SmartTabLayout Demo2][demo2_gif]
![SmartTabLayout Demo3][demo3_gif] ![SmartTabLayout Demo4][demo4_gif]
![SmartTabLayout Demo5][demo5_gif] ![SmartTabLayout Demo6][demo6_gif]
![SmartTabLayout Demo7][demo7_gif]


Try out the sample application on the Play Store.

[![Get it on Google Play][googleplay_store_badge]][demo_app]


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
                android:layout_height="wrap_content">

        </br.com.soluevo.tabpagedatelibrary.months.MonthCustomView>

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

        customView = month_custom_view_id
        customView.handler = this
        customView.getMonths(
            "id cookie",
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

