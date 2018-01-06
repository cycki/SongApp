package pl.mkwiecinski.songapp.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.mkwiecinski.songapp.data.SongEntity
import javax.inject.Inject

class SongEntityMapper @Inject constructor() {
    fun map(raw: String): List<SongEntity> {
        val type = object : TypeToken<List<SongEntity>>() {}.type
        return Gson().fromJson(raw, type)
    }
}
