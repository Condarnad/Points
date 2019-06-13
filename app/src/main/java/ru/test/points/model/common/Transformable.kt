package ru.test.points.model.common

import io.reactivex.Single

interface Transformable<T> {
    fun transform(): T
}

fun <T : Collection<Transformable<R>>, R> Single<T>.transformCollection() =
    map { it.map(Transformable<R>::transform) }