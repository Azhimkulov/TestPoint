package com.siroca.data.repository

import com.siroca.data.mapper.BaseMapper
import com.siroca.data.model.BaseModel
import com.siroca.domain.entity.BaseEntity
import com.siroca.domain.repository.BaseRepository
import io.reactivex.Observable

/**
 * Created by azamat  on 9/15/19.
 */
class SpendResultRepository(
    private val factory: BaseRemote<BaseModel>,
    private val baseMapper: BaseMapper
) : BaseRepository<BaseEntity> {

    override fun getBase(status:Int): Observable<BaseEntity> {
        return factory.getBase(status).map { list ->
            baseMapper.mapFromEntity(list)
        }
    }
}