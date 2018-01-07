package pl.mkwiecinski.songapp.domain.repository

import io.reactivex.Single
import pl.mkwiecinski.songapp.domain.models.LibraryModel

interface ILocalSongsRepository {
    fun all(searchQuery: String): Single<LibraryModel>
}
