package pl.mkwiecinski.songapp.di.modules

import dagger.Module
import dagger.Provides
import pl.mkwiecinski.songapp.api.endpoints.ILibraryEndpoint
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class]) class EndpointsModule {
    @Provides fun provideApi(retrofit: Retrofit): ILibraryEndpoint {
        return retrofit.create(ILibraryEndpoint::class.java)
    }
}
