package com.menilv.reddit.di
import com.menilv.reddit.di.application.MainApplicationComponent
import com.menilv.reddit.di.application.MainApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainApplicationModule::class])
interface ApplicationComponent : MainApplicationComponent
