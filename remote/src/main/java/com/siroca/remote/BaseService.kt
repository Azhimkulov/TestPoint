package com.siroca.remote

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.PUT

/**
 * Defines the abstract methods used for interacting with the Base API
 */
interface BaseService {

    @PUT("operationStatus")
    fun putResult(@Body body: BaseRemote): Observable<BaseRemote>

    class BaseRemote(val status:Int)
}
