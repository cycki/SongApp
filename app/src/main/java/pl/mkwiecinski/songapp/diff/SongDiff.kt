package pl.mkwiecinski.songapp.diff

import pl.mkwiecinski.songapp.domain.models.SongModel

class SongDiff : DiffCallback<SongModel> {
    override fun areItemsTheSame(old: SongModel, new: SongModel) = areContentsTheSame(old, new)

    override fun areContentsTheSame(old: SongModel, new: SongModel) = old == new
}
