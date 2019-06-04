# Tab Page Date Component
Tabs with month and year

### Project Details
 * Project created with Androidx
 * Retrofit
 * Dagger 2
 * MVVM
 * Data Binding
 * Custom view
 
# Usage

In your build.gradle(Project: android).
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

```

Add in your build.gradle(Module: app) 

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
        customView.getMonthsFromActivity(
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

# Functions information 

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

Get the title based on the date and check if you have any current date to return the title "Hoje"

```java

customView.getTitle(monthResponse)

```

Checks if it is "Hoje"

```java

customView.isToday(monthResponse)

```

Add an instance of fragment and title

```java

customView.sectionsPagerAdapter.addFragment(InstanceFragment(), "your title")

```

Updates dates with dates set

```java

 customView.sectionsPagerAdapter.notifyDataSetChanged()

```

Clears the observable after the fragment or activity is destroyed

```java

 customView.clearDisposable()

```
