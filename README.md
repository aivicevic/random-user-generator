# Random User Generator
Android application to generate random user profiles. Allows saving of profiles to "Favorites" for later viewing. Favorites will be cleared upon uninstallation of application as they are stored in a local database.

**Important Note:** Some functionality is not yet complete in regards to the saving of "Favorite" users. See comments in various class files for more details.

## Architecture
* [CLEAN Architecture](https://proandroiddev.com/android-clean-architecture-with-viewmodel-usecases-and-repositories-part-1-b9e63889a1aa)
* [MVVM + Koin + DataBinding](https://aish05.medium.com/mvvm-koin-and-architecture-components-in-kotlin-6251100e2285)

## Dependencies
* [LifeCycle Components](https://developer.android.com/topic/libraries/architecture/adding-components) - LiveData and ViewModel classes
* [Koin](https://insert-koin.io/) - Dependency injection
* [Retrofit](https://square.github.io/retrofit/) + [OkHttp3](http://square.github.io/okhttp/) - REST API integration
* [Coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiA24SPBhB0EiwAjBgkhsAdLqsM61mw_ra3znopcFlODHYP8POJ6Si4h6vnYEjp-E9q2PsmSBoC4g8QAvD_BwE&gclsrc=aw.ds) - Reactive programming
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Local database implementation
* [Glide](https://github.com/bumptech/glide) - Image loading
