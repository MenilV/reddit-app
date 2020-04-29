package com.menilv.reddit.di.activity


import androidx.appcompat.app.AppCompatActivity
import com.menilv.reddit.MainActivity
import com.menilv.reddit.di.ApplicationComponent
import com.menilv.reddit.di.application.MainApplicationComponent
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent : MainApplicationComponent {
    fun inject(activity: MainActivity)
    fun activity(): AppCompatActivity
}
