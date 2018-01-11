package pl.mkwiecinski.songapp.api.mappers

import pl.mkwiecinski.songapp.api.models.SearchResultApiModel
import pl.mkwiecinski.songapp.api.models.SingleResultModel
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import javax.inject.Inject

class SongApiMapper @Inject constructor() {
    /**
     *  Maps [api] object.
     *
     *  @param[api] object being transformed
     *
     *  @return [LibraryModel] corresponding to specified object
     */
    fun map(api: SearchResultApiModel): LibraryModel {
        return api.results.orEmpty().map(this::map).let(::LibraryModel)
    }

    /**
     *  Transforms single [api] object to [SongModel]
     *
     *  @param[api] object that needs to be converted to [SongModel].
     *
     *  @return [SongModel] corresponding to specified object
     */
    private fun map(api: SingleResultModel): SongModel {
        return api.let {
            SongModel(name = it.name ?: "", artist = it.artist ?: "", year = it.year?.year)
        }
    }
}
