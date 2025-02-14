package com.menilv.reddit.di.fragment

import android.content.Intent
import androidx.navigation.NavController

interface Navigator {
  fun getNavController(): NavController
  fun getParentNavController(): NavController?
  fun startActivityIntent(intent: Intent)
}
