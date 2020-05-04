## [ reddit app ] for tinybeans

A simple android app that consumes the Reddit API (https://www.reddit.com/.json) in order to show a list of front page posts from Reddit.

This project brings to table set of best practices, tools, and solutions:

* pure Kotlin
* Model-View-Intent Architecture
* Android Jetpack
* Dependency Injection
* Material design
 

 ## Tech-stack
 
This project takes advantage of many popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version.
 
 * Tech-stack
     * [Kotlin](https://kotlinlang.org/)
     * [Dagger](https://https://dagger.dev/) - dependency injection
     * [Jetpack](https://developer.android.com/jetpack) - navigation
     * [RxJava](https://github.com/ReactiveX/RxAndroid) - reactive programming and background operations
     * [LeakCanary](https://square.github.io/leakcanary/) - leak detection/prevention - available on debug build only!!!
 
 * Architecture
     * [MVI]([https://www.raywenderlich.com/817602-mvi-architecture-for-android-tutorial-getting-started](https://www.raywenderlich.com/817602-mvi-architecture-for-android-tutorial-getting-started)) - application level
    
 ## Getting started
 
 This project was written with Android Studio 3.5.3 (Build #AI-191.8026.42.35.6010548) and Gradle Plugin (3.4.1) and is compatible
 all the way to Android 21 (Lollipop). It also includes 3rd party dependencies which include (but not limited to):
 * Dagger (2.2.1)
 * Retrofit(3.4.12) + OkHttp (3.14.2) + OkHttpLoggingInterceptor (3.4.12)
 * Moshi (1.8.0)
 * RxJava (2.2.12) + RxLifecycle (2.0.4) + RxBindings (3.0.0-alpha2)
 * AndroidX navigation (2.3.0-alpha04)
 * Glide (4.11.0)


Appart for the above mentioned dependencies this project requires nothing else to be ran on an Android enabled device.
 
 ### Android Studio
 
 1. Android Studio -> File -> New -> From Version control -> Git
 2. Enter `https://github.com/MenilV/reddit-app` into URL field
 3. Run default configuration
 
 ### Command line + Android Studio
 
 1. Run `git clone https://github.com/MenilV/reddit-app`
 2. Android Studio -> File -> Open
 3. Run default configuration
 
