package pl.mkwiecinski.songapp.adapters

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.mkwiecinski.songapp.databinding.ListSongItemBinding
import pl.mkwiecinski.songapp.domain.models.SongModel
import pl.mkwiecinski.songapp.shared.ListChangedAdapterCallback

class SongsAdapter(private val songs: ObservableList<SongModel>) : RecyclerView.Adapter<SongViewHolder>() {
    init {
        songs.addOnListChangedCallback(ListChangedAdapterCallback(this))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongViewHolder {
        return LayoutInflater.from(parent?.context).let {
            ListSongItemBinding.inflate(it, parent, false)
        }.let(::SongViewHolder)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongViewHolder?, position: Int) {
        holder ?: return
        holder.binding.model = songs[position]
    }
}

class SongViewHolder(val binding: ListSongItemBinding) : RecyclerView.ViewHolder(binding.root)
