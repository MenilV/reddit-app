package com.menilv.reddit.di.fragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
open class FragmentModule(val fragment: Fragment) {
  @Provides @PerFragment
  fun navController(navigator: NavigatorImpl) : Navigator = navigator

  @Provides @PerFragment
  fun fragment() : Fragment = fragment
}
