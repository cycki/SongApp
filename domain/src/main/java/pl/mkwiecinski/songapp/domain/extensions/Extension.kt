package pl.mkwiecinski.songapp.domain.extensions

import java.util.*


inline fun <reified E : Enum<E>> enumSetOfNone(): EnumSet<E> = EnumSet.noneOf(E::class.java)
inline fun <reified E : Enum<E>> enumSetOfAll(): EnumSet<E> = EnumSet.allOf(E::class.java)
