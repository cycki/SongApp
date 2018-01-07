package pl.mkwiecinski.songapp.domain

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import pl.mkwiecinski.songapp.domain.models.GetSongsParam
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import pl.mkwiecinski.songapp.domain.usecases.SourceType
import java.util.*

class DomainLayerUnitTest {
    lateinit var schedulers: ISchedulerProvider

    @Before fun setupSchedulers() {
        schedulers = object : ISchedulerProvider {
            override val worker = Schedulers.trampoline()
            override val postExecution = Schedulers.trampoline()
        }
    }

    @Test fun execute_LocalSourceOnly_LocalRepositoryCalled() {
        val local = mock<ILocalSongsRepository> {
            on { all(any()) } doReturn Single.just(LibraryModel(listOf(mock(), mock())))
        }
        val remote = mock<IRemoteSongsRepository> { }
        val param = GetSongsParam(EnumSet.of(SourceType.Local), "")
        val useCase = GetSongsUseCase(schedulers = schedulers, local = local, remote = remote)

        val testing = useCase.execute(param).test()

        testing.awaitTerminalEvent()
        testing.assertNoErrors()
        testing.assertValue { it.size == 2 }
        verify(remote, never()).all(any())
    }
}
