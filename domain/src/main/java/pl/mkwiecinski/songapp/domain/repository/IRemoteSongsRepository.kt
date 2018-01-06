package pl.mkwiecinski.songapp.domain.repository

import io.reactivex.Single
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel

interface IRemoteSongsRepository {
    fun all(): Single<LibraryModel>
}