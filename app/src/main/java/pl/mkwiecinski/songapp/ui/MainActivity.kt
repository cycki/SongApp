package pl.mkwiecinski.songapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import pl.mkwiecinski.songapp.R
import pl.mkwiecinski.songapp.adapters.SongsAdapter
import pl.mkwiecinski.songapp.databinding.ActivityMainBinding
import pl.mkwiecinski.songapp.di.factories.MainViewModelFactory
import pl.mkwiecinski.songapp.shared.addDisposable
import pl.mkwiecinski.songapp.shared.execute
import pl.mkwiecinski.songapp.shared.value
import pl.mkwiecinski.songapp.ui.dialogs.FiltersDialogFragment
import pl.mkwiecinski.songapp.vm.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val disposeBag = CompositeDisposable()

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    @Inject lateinit var factory: MainViewModelFactory

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModel(savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        viewModel.loadCommand.error.subscribe {
            Snackbar.make(binding.root, R.string.error, Snackbar.LENGTH_SHORT).show()
        }.addDisposable(disposeBag)
        savedInstanceState ?: viewModel.loadCommand.execute()
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel
        setSupportActionBar(binding.toolbar)

        binding.list.adapter = SongsAdapter(viewModel.songs)
        binding.list.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchView = (menu?.findItem(R.id.search)?.actionView as? SearchView)?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchQuery.value = newText
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.filters -> {
                FiltersDialogFragment(context = this, selected = viewModel.filters).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}


