package pl.mkwiecinski.songapp.di.factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.mkwiecinski.songapp.vm.MainViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val mainViewModel: MainViewModel) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return mainViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
