package pl.mkwiecinski.songapp.di.modules

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.songapp.api.repositiories.RemoteSongsRepository
import pl.mkwiecinski.songapp.data.LocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository

@Module abstract class MainRepositoriesModule {
    @Binds abstract fun provideLocalRepository(c: LocalSongsRepository): ILocalSongsRepository
    @Binds abstract fun provideRemoteRepository(c: RemoteSongsRepository): IRemoteSongsRepository
}
