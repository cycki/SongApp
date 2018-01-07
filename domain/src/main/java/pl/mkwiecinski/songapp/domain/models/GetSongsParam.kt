package pl.mkwiecinski.songapp.domain.models

import pl.mkwiecinski.songapp.domain.usecases.SourceType
import java.util.*

class GetSongsParam(val source: EnumSet<SourceType>, val searchQuery: String)
