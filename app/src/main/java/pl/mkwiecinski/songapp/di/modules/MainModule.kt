package pl.mkwiecinski.songapp.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import pl.mkwiecinski.songapp.domain.ISchedulerProvider
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import javax.inject.Named

@Module(includes = [MainRepositoriesModule::class, SchedulersModule::class]) class MainModule {
    @Provides fun provideUseCase(local: ILocalSongsRepository, @Named(SchedulersModule.IO) schedulers: ISchedulerProvider): GetSongsUseCase {
        return GetSongsUseCase(remote = object : IRemoteSongsRepository {
            override fun all(): Single<LibraryModel> {
                return Single.just(LibraryModel(songs = listOf(SongModel("remote",
                                                                         artist = "artist",
                                                                         year = 1000))))
            }
        }, local = local, schedulers = schedulers)
    }
}
