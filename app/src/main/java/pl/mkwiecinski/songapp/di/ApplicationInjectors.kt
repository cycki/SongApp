package pl.mkwiecinski.songapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.mkwiecinski.songapp.SongApplication
import pl.mkwiecinski.songapp.di.modules.MainModule
import pl.mkwiecinski.songapp.ui.MainActivity

@Module abstract class ApplicationInjectors {
    @Binds abstract fun context(app: SongApplication): Context

    @ActivityScope @ContributesAndroidInjector(modules = [MainModule::class]) abstract fun main(): MainActivity
}
