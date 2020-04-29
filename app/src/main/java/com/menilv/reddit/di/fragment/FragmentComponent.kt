package com.menilv.reddit.di.fragment


import androidx.fragment.app.Fragment
import com.menilv.reddit.di.activity.ActivityComponent
import dagger.Component

@PerFragment
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent : ActivityComponent {
    fun navigator() : Navigator
    fun fragment() : Fragment
}
