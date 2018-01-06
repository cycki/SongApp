package pl.mkwiecinski.songapp.domain.usecases

import io.reactivex.Single
import pl.mkwiecinski.songapp.domain.ISchedulerProvider
import pl.mkwiecinski.songapp.domain.extensions.enumSetOf
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository
import java.util.*

class GetSongsUseCase(private val remote: IRemoteSongsRepository,
                      private val local: ILocalSongsRepository,
                      private val schedulers: ISchedulerProvider) {
    fun execute(sourceType: EnumSet<SourceType>): Single<List<SongModel>> {
        val sources = if (sourceType.isEmpty()) {
            enumSetOf<SourceType>()
        } else {
            sourceType
        }.map {
            when (it) {
                SourceType.Local -> local.all()
                SourceType.Remote -> remote.all()
            }
        }
        return Single.zip(sources, {
            it.map { it as LibraryModel }.flatMap { it.songs }
        }).subscribeOn(schedulers.worker).observeOn(schedulers.postExecution)
    }
}

enum class SourceType {
    Local, Remote
}

