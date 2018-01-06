package pl.mkwiecinski.songapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.songapp.di.DaggerApplicationComponent

class SongApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<SongApplication>? {
        return DaggerApplicationComponent.builder().create(this)
    }
}