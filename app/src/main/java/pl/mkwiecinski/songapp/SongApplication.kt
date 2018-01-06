package pl.mkwiecinski.songapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.songapp.di.DaggerApplicationComponent
import timber.log.Timber

class SongApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<SongApplication>? {
        return DaggerApplicationComponent.builder().create(this)
    }
}