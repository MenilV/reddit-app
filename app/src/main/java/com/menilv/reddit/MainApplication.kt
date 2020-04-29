package com.menilv.reddit
import android.app.Application
import android.content.SharedPreferences
import com.menilv.reddit.di.ApplicationComponent
import com.menilv.reddit.di.application.MainApplicationModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.menilv.reddit.di.DaggerApplicationComponent

class MainApplication : Application() {

  val applicationComponentBuilder: DaggerApplicationComponent.Builder by lazy {
    DaggerApplicationComponent.builder()
        .mainApplicationModule(
            MainApplicationModule(
                this,
                BuildConfig.API,
                sharedPreferences
            )
        )
  }

  val sharedPreferences: SharedPreferences by lazy {
    getSharedPreferences(this.packageName, 0)
  }

  val applicationComponent: ApplicationComponent by lazy { applicationComponentBuilder.build() }

  override fun onCreate() {
    super.onCreate()
    applicationComponent.inject(this)
    Logger.addLogAdapter(AndroidLogAdapter())
  }

}
