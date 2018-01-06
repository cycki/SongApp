package pl.mkwiecinski.songapp.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.songapp.SongApplication
import javax.inject.Singleton

@Singleton @Component(modules = [AndroidSupportInjectionModule::class, ApplicationInjectors::class]) interface ApplicationComponent :
        AndroidInjector<SongApplication> {

    @Component.Builder abstract class Builder : AndroidInjector.Builder<SongApplication>()
}
