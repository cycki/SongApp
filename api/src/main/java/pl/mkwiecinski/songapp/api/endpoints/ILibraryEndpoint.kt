package pl.mkwiecinski.songapp.api.endpoints

import io.reactivex.Single
import pl.mkwiecinski.songapp.api.models.SearchResultApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ILibraryEndpoint {
    @GET("search") fun search(@Query("term") query: String, @Query("limit") limit: Int? = null): Single<SearchResultApiModel>
}
