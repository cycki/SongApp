package pl.mkwiecinski.songapp.api.endpoints

import io.reactivex.Single
import pl.mkwiecinski.songapp.api.models.SearchResultApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ILibraryEndpoint {
    /**
     *  Makes search with [query] to remote api.
     *
     *  @param[query] requested search value
     *  @param[limit] optional value that limit search results
     *
     *  @return [Single] that succeeds with search result
     */
    @GET("search") fun search(@Query("term") query: String, @Query("limit") limit: Int? = null): Single<SearchResultApiModel>
}
