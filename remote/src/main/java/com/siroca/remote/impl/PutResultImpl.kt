package com.siroca.remote.impl

import com.siroca.data.model.BaseModel
import com.siroca.data.repository.BaseRemote
import com.siroca.remote.BaseService
import com.siroca.remote.mapper.BaseEntityMapper
import io.reactivex.Observable

/**
 * Created by azamat  on 9/15/19.
 */
class PutResultImpl(private val baseService: BaseService,
                    private val entityMapper: BaseEntityMapper
) : BaseRemote<BaseModel> {

    override fun getBase(status: Int): Observable<BaseModel> {
        return baseService.putResult(BaseService.BaseRemote(status)).map {
            entityMapper.mapFromRemote(it)
        }
    }
}