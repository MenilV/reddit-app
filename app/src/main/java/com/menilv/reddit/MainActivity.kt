package com.menilv.reddit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.menilv.reddit.di.activity.ActivityComponent
import com.menilv.reddit.di.activity.ActivityModule
import com.menilv.reddit.di.activity.DaggerActivityComponent
import kotlinx.android.synthetic.main.activity_main.*

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

    fun showLoader() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoader() {
        loading.visibility = View.GONE
    }
}
