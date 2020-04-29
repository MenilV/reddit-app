package com.menilv.reddit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.menilv.reddit.di.activity.ActivityComponent
import com.menilv.reddit.di.activity.ActivityModule
import com.menilv.reddit.di.activity.DaggerActivityComponent

class MainActivity : AppCompatActivity() {

    val activityComponent: ActivityComponent by lazy {

        DaggerActivityComponent.builder()
            .applicationComponent((this.application as MainApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)
    }

    public fun showLoader() {

    }

    public fun hideLoader() {

    }
}
