package com.siroca.data.repository

import io.reactivex.Observable

interface BaseRemote<T> {

    fun getBase(status:Int): Observable<T>
}