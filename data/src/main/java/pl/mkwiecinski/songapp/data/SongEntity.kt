package pl.mkwiecinski.songapp.data

import com.google.gson.annotations.SerializedName

class SongEntity {
    @SerializedName("Song Clean")
    var name: String? = null

    @SerializedName("ARTIST CLEAN")
    var artist: String? = null

    @SerializedName("Release Year")
    var year: String? = null
}
