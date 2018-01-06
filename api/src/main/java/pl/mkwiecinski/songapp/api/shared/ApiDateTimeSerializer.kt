package pl.mkwiecinski.songapp.api.shared

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import timber.log.Timber
import java.lang.reflect.Type

class ApiDateTimeSerializer : JsonDeserializer<DateTime?>, JsonSerializer<DateTime?> {
    private val format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override fun serialize(src: DateTime?,
                           typeOfSrc: Type?,
                           context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.let {
            format.withZone(DateTimeZone.getDefault()).print(it.withZone(DateTimeZone.UTC))
        })
    }

    override fun deserialize(json: JsonElement?,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): DateTime? {
        return json?.asString?.let {
            try {
                format.withZone(DateTimeZone.UTC).parseDateTime(it).withZone(DateTimeZone.getDefault())
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }
    }
}
