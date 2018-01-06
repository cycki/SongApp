package pl.mkwiecinski.songapp.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.mkwiecinski.songapp.domain.ISchedulerProvider
import javax.inject.Named

@Module class SchedulersModule {
    companion object {
        const val COMPUTATION = "worker"
        const val IO = "io"
    }

    @Provides @Named(COMPUTATION) fun provideComputation(): ISchedulerProvider {
        return object : ISchedulerProvider {
            override val worker = Schedulers.computation()
            override val postExecution = AndroidSchedulers.mainThread()
        }
    }

    @Provides @Named(IO) fun provideIO(): ISchedulerProvider {
        return object : ISchedulerProvider {
            override val worker = Schedulers.io()
            override val postExecution = AndroidSchedulers.mainThread()
        }
    }
}
