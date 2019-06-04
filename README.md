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

_(For a working implementation of this project see the demo/ folder.)_

Add the dependency to your build.gradle.

```
// For androidx (1.0.0)
dependencies {
    compile 'com.ogaclejapan.smarttablayout:library:2.0.0@aar'

    //Optional: see how to use the utility.
    compile 'com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar'
}

// For legacy android support library (28.0.0)
dependencies {
    compile 'com.ogaclejapan.smarttablayout:library:1.7.0@aar'

    //Optional: see how to use the utility.
    compile 'com.ogaclejapan.smarttablayout:utils-v4:1.7.0@aar'

    //Deprecated since 1.7.0
    compile 'com.ogaclejapan.smarttablayout:utils-v13:1.7.0@aar'
}
```

Include the SmartTabLayout widget in your layout.
This should usually be placed above the ViewPager it represents.

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

In your onCreate method (or onCreateView for a fragment), bind the widget to the ViewPager.
(If you use a utility together, you can easily add items to PagerAdapter)

e.g. ViewPager of v4.Fragment

```Java

    private lateinit var customView: MonthCustomView
    
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)
   
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

```

(Optional) If you use an OnPageChangeListener with your view pager you should set it in the widget rather than on the pager directly.


```java

viewPagerTab.setOnPageChangeListener(mPageChangeListener);

```

(Optional) Using the FragmentPagerItemAdapter of utility, you will be able to get a position in the Fragment side.

```java

int position = FragmentPagerItem.getPosition(getArguments());

```

This position will help to implement the parallax scrolling header that contains the ViewPager :P

# How to customize the tab

The customization of tab There are three ways.

* Customize the attribute
* SmartTabLayout#setCustomTabView(int layoutResId, int textViewId)
* SmartTabLayout#setCustomTabView(TabProvider provider)

If set the TabProvider, can build a view for each tab.

```java

public class SmartTabLayout extends HorizontalScrollView {

    //...

    /**
     * Create the custom tabs in the tab layout. Set with
     * {@link #setCustomTabView(com.ogaclejapan.smarttablayout.SmartTabLayout.TabProvider)}
     */
    public interface TabProvider {

        /**
         * @return Return the View of {@code position} for the Tabs
         */
        View createTabView(ViewGroup container, int position, PagerAdapter adapter);

    }

    //...
}

```

# How to use the utility

Utility has two types available to suit the Android support library.

* utils-v4 library contains the PagerAdapter implementation class for _androidx.fragment.app.Fragment_
* (Deprecated) utils-v13 library contains the PagerAdapter implementation class for _android.app.Fragment_

The two libraries have different Android support libraries that depend,
but implemented functionality is the same.

## PagerAdapter for View-based Page

```Kotlin

ViewPagerItemAdapter adapter = new ViewPagerItemAdapter(ViewPagerItems.with(this)
        .add(R.string.title, R.layout.page)
        .add("title", R.layout.page)
        .create());

viewPager.setAdapter(adapter);

//...

public void onPageSelected(int position) {

  //.instantiateItem() from until .destroyItem() is called it will be able to get the View of page.
  View page = adapter.getPage(position);

}


```

## PagerAdapter for Fragment-based Page

Fragment-based PagerAdapter There are two implementations.
Please differences refer to the library documentation for Android.

* FragmentPagerItemAdapter extends FragmentPagerAdapter
* FragmentStatePagerItemAdapter extends FragmentStatePagerAdapter

```java

FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
        getSupportFragmentManager(), FragmentPagerItems.with(this)
        .add(R.string.title, PageFragment.class),
        .add(R.string.title, WithArgumentsPageFragment.class, new Bundler().putString("key", "value").get()),
        .add("title", PageFragment.class)
        .create());

viewPager.setAdapter(adapter);

//...

public void onPageSelected(int position) {

  //.instantiateItem() from until .destoryItem() is called it will be able to get the Fragment of page.
  Fragment page = adapter.getPage(position);

}

```

*__Notes:__ If using fragment inside a ViewPager, Must be use [Fragment#getChildFragmentManager()](https://developer.android.com/reference/androidx/fragment/app/Fragment.html#getChildFragmentManager).*


# Looking for iOS ?
 Check [WormTabStrip](https://github.com/EzimetYusup/WormTabStrip) out.
 
# LICENSE

```
Copyright (C) 2015 ogaclejapan
Copyright (C) 2013 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo1_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo1.gif
[demo2_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo2.gif
[demo3_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo3.gif
[demo4_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo4.gif
[demo5_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo5.gif
[demo6_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo6.gif
[demo7_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo7.gif
[demo_app]: https://play.google.com/store/apps/details?id=com.ogaclejapan.smarttablayout.demo&referrer=utm_source%3Dgithub
[demo_icon]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/icon.png
[googleplay_store_badge]: http://www.android.com/images/brand/get_it_on_play_logo_large.png
[maven_central_badge_svg]: https://maven-badges.herokuapp.com/maven-central/com.ogaclejapan.smarttablayout/library/badge.svg?style=flat
[maven_central_badge_app]: https://maven-badges.herokuapp.com/maven-central/com.ogaclejapan.smarttablayout/library
[android_arsenal_badge_svg]: https://img.shields.io/badge/Android%20Arsenal-SmartTabLayout-brightgreen.svg?style=flat
[android_arsenal_badge_link]: http://android-arsenal.com/details/1/1683
[android_weekly_badge_svg]: https://img.shields.io/badge/AndroidWeekly-%23148-blue.svg
[android_weekly_badge_link]: http://androidweekly.net/issues/issue-148
[qiitanium]: https://github.com/ogaclejapan/Qiitanium
[google_slidingtabbasic]: https://github.com/googlesamples/android-SlidingTabsBasic
