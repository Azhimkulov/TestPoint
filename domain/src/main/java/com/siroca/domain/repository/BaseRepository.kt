package com.siroca.domain.repository

import io.reactivex.Observable

interface BaseRepository<T> {
    fun getBase(status:Int): Observable<T>
}