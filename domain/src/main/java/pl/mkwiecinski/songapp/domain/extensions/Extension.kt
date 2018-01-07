package pl.mkwiecinski.songapp.domain.extensions

import java.util.*


inline fun <reified E : Enum<E>> enumSetOf(): EnumSet<E> = EnumSet.noneOf(E::class.java)
inline fun <reified E : Enum<E>> enumSetOfAll(): EnumSet<E> = EnumSet.allOf(E::class.java)

fun <E : Enum<E>> enumSetOf(single: E): MutableSet<E> = EnumSet.of(single)

fun <E : Enum<E>> enumSetOf(e0: E, e1: E): MutableSet<E> = EnumSet.of(e0, e1)

fun <E : Enum<E>> enumSetOf(e0: E, e1: E, e2: E): MutableSet<E> = EnumSet.of(e0, e1, e2)
