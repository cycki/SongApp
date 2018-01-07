package pl.mkwiecinski.songapp.api

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Test
import pl.mkwiecinski.songapp.api.endpoints.ILibraryEndpoint
import pl.mkwiecinski.songapp.api.mappers.SongApiMapper
import pl.mkwiecinski.songapp.api.models.SearchResultApiModel
import pl.mkwiecinski.songapp.api.repositiories.RemoteSongsRepository
import pl.mkwiecinski.songapp.domain.models.LibraryModel

class DataLayerUnitTest {
    @Test fun all_apiCalled() {
        val query = "some query"
        val apiResult = SearchResultApiModel()
        val api = mock<ILibraryEndpoint> {
            on { search(eq(query), anyOrNull()) } doReturn Single.just(apiResult)
        }
        val mapper = mock<SongApiMapper> {
            on { map(eq(apiResult)) } doReturn mock<LibraryModel>()
        }
        val repository = RemoteSongsRepository(api = api, mapper = mapper)

        val testing = repository.all(query).test()

        testing.awaitTerminalEvent()
        verify(api).search(anyOrNull(), anyOrNull())
    }
}