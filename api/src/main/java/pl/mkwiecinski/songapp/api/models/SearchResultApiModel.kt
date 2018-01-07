package pl.mkwiecinski.songapp.api.models

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

class SearchResultApiModel {
    @SerializedName("results")
    var results: List<SingleResultModel>? = null
}

class SingleResultModel {
    @SerializedName("trackName")
    var name: String? = null
    @SerializedName("artistName")
    var artist: String? = null
    @SerializedName("releaseDate")
    var year: DateTime? = null
}
