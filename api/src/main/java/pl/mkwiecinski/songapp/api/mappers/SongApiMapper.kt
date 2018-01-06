package pl.mkwiecinski.songapp.api.mappers

import pl.mkwiecinski.songapp.api.models.SearchResultApiModel
import pl.mkwiecinski.songapp.api.models.SingleResultModel
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import javax.inject.Inject

class SongApiMapper @Inject constructor() {
    fun map(api: SearchResultApiModel): LibraryModel {
        return (api.results?.map(this::map) ?: listOf<SongModel>()).let(::LibraryModel)
    }

    private fun map(api: SingleResultModel): SongModel {
        return api.let {
            SongModel(name = it.name ?: "", artist = it.artist ?: "", year = it.year?.year)
        }
    }
}
