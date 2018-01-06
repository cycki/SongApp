package pl.mkwiecinski.songapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import org.joda.time.DateTime
import pl.mkwiecinski.songapp.api.BuildConfig
import pl.mkwiecinski.songapp.api.shared.ApiDateTimeSerializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module class RetrofitModule {
    @Named("api") @Provides fun gson(): Gson = GsonBuilder().apply {
        registerTypeAdapter(DateTime::class.java, ApiDateTimeSerializer())
    }.create()

    @Singleton @Provides fun provideRetrofit(@Named("api") gson: Gson): Retrofit {
        return Retrofit.Builder().apply {
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
            baseUrl(BuildConfig.API_URL)
        }.build()
    }

}

