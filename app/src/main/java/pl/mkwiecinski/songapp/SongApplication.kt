package pl.mkwiecinski.songapp

import com.squareup.leakcanary.LeakCanary
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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    override fun applicationInjector(): AndroidInjector<SongApplication>? {
        return DaggerApplicationComponent.builder().create(this)
    }
}