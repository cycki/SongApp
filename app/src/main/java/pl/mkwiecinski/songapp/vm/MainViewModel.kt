package pl.mkwiecinski.songapp.vm

import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.mkwiecinski.rxcommand.RxCommand
import pl.mkwiecinski.songapp.di.ActivityScope
import pl.mkwiecinski.songapp.diff.DiffObservableList
import pl.mkwiecinski.songapp.diff.SongDiff
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import pl.mkwiecinski.songapp.domain.usecases.SourceType
import java.util.*
import javax.inject.Inject

@ActivityScope class MainViewModel @Inject constructor(private val getSongs: GetSongsUseCase) :
        ViewModel() {
    val songs = DiffObservableList(SongDiff())

    val loadCommand = RxCommand(this::load)

    private fun load(param: Unit): Single<Unit> {
        return getSongs.execute(EnumSet.of(SourceType.Remote)).observeOn(Schedulers.computation()).doOnSuccess {
            songs.value = it
        }.map { }
    }
}
