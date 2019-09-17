package com.siroca.data.mapper

import com.siroca.data.model.BaseModel
import com.siroca.domain.entity.BaseEntity

/**
 * Created by azamat  on 9/15/19.
 */
class BaseMapper:Mapper<BaseModel, BaseEntity> {
    override fun mapFromEntity(type: BaseModel): BaseEntity {
        return BaseEntity(type.status)
    }

    override fun mapToEntity(type: BaseEntity): BaseModel {
        return BaseModel(type.status)
    }
}