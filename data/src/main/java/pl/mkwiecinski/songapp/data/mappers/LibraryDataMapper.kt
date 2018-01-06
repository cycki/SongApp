package pl.mkwiecinski.songapp.data.mappers

import pl.mkwiecinski.songapp.data.SongEntity
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import javax.inject.Inject

class LibraryDataMapper @Inject constructor() {
    fun map(param: List<SongEntity>): LibraryModel {
        return param.map(this::map).let(::LibraryModel)
    }

    private fun map(entity: SongEntity) = entity.let {
        SongModel(name = it.name ?: "", year = it.year?.toIntOrNull(), artist = it.artist ?: "")
    }
}
