package pl.mkwiecinski.songapp.api.shared

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import timber.log.Timber
import java.lang.reflect.Type

class ApiDateTimeSerializer : JsonDeserializer<DateTime?>, JsonSerializer<DateTime?> {
    private val format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    /**
     * Serializes specified [src] with UTC zone to json object.
     *
     * @param[src] object that needs to be converted to Json.
     * @param[typeOfSrc] actual type of the source object.
     * @param[context] context used for this serialization
     *
     * @return mapped object corresponding to specified param
     */
    override fun serialize(src: DateTime?,
                           typeOfSrc: Type?,
                           context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.let {
            format.withZone(DateTimeZone.getDefault()).print(it.withZone(DateTimeZone.UTC))
        })
    }

    /**
     * Tries to map valid [json] data to [DateTime] given that input was provided in UTC time
     * in specified format
     *
     * @param[json] json data being deserialized
     * @param[typeOfT] type of Object to deserialize to
     * @param[context] context used for this deserialization
     *
     * @return DateTime object or null if parsing failed.
     */
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
