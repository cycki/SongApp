package pl.mkwiecinski.songapp.data

import android.content.Context
import io.reactivex.Single
import pl.mkwiecinski.songapp.R
import pl.mkwiecinski.songapp.data.mappers.LibraryDataMapper
import pl.mkwiecinski.songapp.data.mappers.SongEntityMapper
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import javax.inject.Inject

class LocalSongsRepository @Inject constructor(c: Context,
                                               private val entityMapper: SongEntityMapper,
                                               private val dataMapper: LibraryDataMapper) :
        ILocalSongsRepository {
    companion object {
        private val FILE_ID = R.raw.songs
    }

    private val resources = c.resources

    override fun all(searchQuery: String): Single<LibraryModel> {
        return Single.create<String> {
            val loaded = resources.openRawResource(FILE_ID).bufferedReader().use {
                it.readText()
            }
            it.onSuccess(loaded)
        }.map(entityMapper::map).map {
            search(it, searchQuery)
        }.map(dataMapper::map)
    }

    private fun search(all: List<SongEntity>, searchQuery: String): List<SongEntity> {
        return all.filter { it.containsQuery(searchQuery) }
    }
}

private fun SongEntity.containsQuery(searchQuery: String): Boolean {
    return searchQuery.split(" ").all {
        when (true) {
            name?.contains(it, ignoreCase = true) -> true
            artist?.contains(it, ignoreCase = true) -> true
            year?.contains(it, ignoreCase = true) -> true
            else -> false
        }
    }
}
