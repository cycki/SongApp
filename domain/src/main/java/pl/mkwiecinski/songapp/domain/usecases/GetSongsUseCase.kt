package pl.mkwiecinski.songapp.domain.usecases

import io.reactivex.Single
import pl.mkwiecinski.songapp.domain.ISchedulerProvider
import pl.mkwiecinski.songapp.domain.extensions.enumSetOfAll
import pl.mkwiecinski.songapp.domain.models.GetSongsParam
import pl.mkwiecinski.songapp.domain.models.LibraryModel
import pl.mkwiecinski.songapp.domain.models.SongModel
import pl.mkwiecinski.songapp.domain.repository.ILocalSongsRepository
import pl.mkwiecinski.songapp.domain.repository.IRemoteSongsRepository

class GetSongsUseCase(private val remote: IRemoteSongsRepository,
                      private val local: ILocalSongsRepository,
                      private val schedulers: ISchedulerProvider) {
    fun execute(param: GetSongsParam): Single<List<SongModel>> {
        val sources = if (param.source.isEmpty()) {
            enumSetOfAll()
        } else {
            param.source
        }.map {
            when (it) {
                SourceType.Local -> local.all(param.searchQuery)
                SourceType.Remote -> remote.all(param.searchQuery)
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

