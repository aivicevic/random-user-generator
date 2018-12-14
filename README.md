# Random User Generator
Android application to generate random user profiles. Allows saving of profiles to "Favorites" for later viewing. Favorites will be cleared upon uninstallation of application as they are stored in a local database.

## Architecture
* [CLEAN Architecture](https://proandroiddev.com/android-clean-architecture-with-viewmodel-usecases-and-repositories-part-1-b9e63889a1aa)
* [MVVM + Dagger 2 + RxJava/RxAndroid](https://proandroiddev.com/mvvm-with-kotlin-android-architecture-components-dagger-2-retrofit-and-rxandroid-1a4ebb38c699)

## Dependencies
* [LifeCycle Extensions](https://developer.android.com/topic/libraries/architecture/adding-components) - LiveData and ViewModel classes
* [Dagger 2](https://google.github.io/dagger/) - Dependency injection
* [Retrofit](https://square.github.io/retrofit/) + [OkHttp3](http://square.github.io/okhttp/) - REST API integration
* [RxJava](https://github.com/ReactiveX/RxJava) + [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Reactive programming
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Local database implementation
* [Glide](https://github.com/bumptech/glide) - Image loading
