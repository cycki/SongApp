package pl.mkwiecinski.songapp.di.modules

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.songapp.data.LocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository

@Module abstract class MainRepositoriesModule {
    @Binds abstract fun provideLocalRepository(c: LocalSongsRepository): ILocalSongsRepository
}
