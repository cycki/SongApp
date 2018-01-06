package pl.mkwiecinski.songapp.di.modules

import dagger.Module
import dagger.Provides
import pl.mkwiecinski.songapp.domain.ISchedulerProvider
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import javax.inject.Named

@Module(includes = [MainRepositoriesModule::class, SchedulersModule::class]) class MainModule {
    @Provides fun provideUseCase(remote: IRemoteSongsRepository,
                                 local: ILocalSongsRepository, @Named(SchedulersModule.IO) schedulers: ISchedulerProvider): GetSongsUseCase {
        return GetSongsUseCase(remote = remote, local = local, schedulers = schedulers)
    }
}
