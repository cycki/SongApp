package pl.mkwiecinski.songapp

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import dagger.android.AndroidInjection
import pl.mkwiecinski.songapp.databinding.ActivityMainBinding
import pl.mkwiecinski.songapp.di.factories.MainViewModelFactory
import pl.mkwiecinski.songapp.vm.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    @Inject lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModel(savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        savedInstanceState ?: viewModel.loadCommand.execute(Unit)
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel
        setSupportActionBar(binding.toolbar)

        binding.list.adapter = SongsAdapter(viewModel.songs)
        binding.list.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}


