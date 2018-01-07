package pl.mkwiecinski.songapp

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import pl.mkwiecinski.songapp.shared.value
import pl.mkwiecinski.songapp.vm.MainViewModel


@RunWith(RobolectricTestRunner::class) class PresentationLayerUnitTest {
    @Before fun setupRxSchedulers() {
        val immediate = Schedulers.trampoline()
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test fun load_QueryNotTrimmed_SearchedTrimmed() {
        val useCase = mock<GetSongsUseCase> {
            on { execute(any()) } doReturn Single.just(listOf())
        }
        val vm = MainViewModel(useCase)
        vm.searchQuery.value = "   search query "
        val error = vm.loadCommand.error.test()

        vm.loadCommand.execute(Unit)

        error.assertNoValues()
        verify(useCase).execute(argThat { searchQuery == "search query" })
    }
}
