package pl.mkwiecinski.songapp.vm

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import pl.mkwiecinski.rxcommand.RxCommand
import pl.mkwiecinski.songapp.di.ActivityScope
import pl.mkwiecinski.songapp.diff.DiffObservableList
import pl.mkwiecinski.songapp.diff.SongDiff
import pl.mkwiecinski.songapp.domain.extensions.enumSetOfAll
import pl.mkwiecinski.songapp.domain.models.GetSongsParam
import pl.mkwiecinski.songapp.domain.usecases.GetSongsUseCase
import pl.mkwiecinski.songapp.domain.usecases.SourceType
import pl.mkwiecinski.songapp.shared.addDisposable
import pl.mkwiecinski.songapp.shared.execute
import pl.mkwiecinski.songapp.shared.value
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope class MainViewModel @Inject constructor(private val getSongs: GetSongsUseCase) :
        ViewModel(),
        Disposable {
    private val disposeBag = CompositeDisposable()
    private val searchSubject = PublishSubject.create<String>()

    val songs = DiffObservableList(SongDiff())
    val searchQuery = ObservableField("")
    val filters = ObservableField(EnumSet.of(SourceType.Local))

    val loadCommand = RxCommand(this::load).addDisposable(disposeBag)

    init {
        searchQuery.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                searchSubject.onNext(searchQuery.value ?: "")
            }
        })
        filters.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {

                loadCommand.stop()
                loadCommand.execute()
            }
        })
        searchSubject.debounce(200, TimeUnit.MILLISECONDS).subscribe {
            loadCommand.stop()
            loadCommand.execute()
        }.addDisposable(disposeBag)
    }

    private fun load(param: Unit): Single<Unit> {
        return GetSongsParam(source = filters.value ?: enumSetOfAll(),
                             searchQuery = searchQuery.value?.trim() ?: "").let {
            getSongs.execute(it)
        }.observeOn(Schedulers.computation()).doOnSuccess {
            songs.value = it
        }.map { }
    }

    override fun isDisposed() = disposeBag.isDisposed

    override fun dispose() = disposeBag.dispose()

    override fun onCleared() {
        dispose()
        super.onCleared()
    }
}
