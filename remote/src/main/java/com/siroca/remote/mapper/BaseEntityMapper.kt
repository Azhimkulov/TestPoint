package com.siroca.remote.mapper

import com.siroca.data.model.BaseModel
import com.siroca.remote.BaseService

/**
 * Created by azamat  on 9/15/19.
 */
class BaseEntityMapper:EntityMapper<BaseService.BaseRemote, BaseModel> {
    override fun mapFromRemote(type: BaseService.BaseRemote): BaseModel {
        return BaseModel(type.status)
    }
}