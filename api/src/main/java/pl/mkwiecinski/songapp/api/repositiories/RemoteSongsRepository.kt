package pl.mkwiecinski.songapp.api.repositiories

import io.reactivex.Single
import pl.mkwiecinski.songapp.api.endpoints.ILibraryEndpoint
import pl.mkwiecinski.songapp.api.mappers.SongApiMapper
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository
import javax.inject.Inject

class RemoteSongsRepository @Inject constructor(private val api: ILibraryEndpoint,
                                                private val mapper: SongApiMapper) :
        IRemoteSongsRepository {
    override fun all(searchQuery: String): Single<LibraryModel> {
        return api.search(searchQuery).map(mapper::map)
    }
}
