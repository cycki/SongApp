package pl.mkwiecinski.songapp.diff

interface DiffCallback<in T> {
    fun areItemsTheSame(old: T, new: T): Boolean
    fun areContentsTheSame(old: T, new: T): Boolean
}