package com.siroca.testpoint.injection

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.siroca.data.model.BaseModel
import com.siroca.data.repository.BaseRemote
import com.siroca.data.repository.SpendResultRepository
import com.siroca.domain.entity.BaseEntity
import com.siroca.domain.interactor.PutSpendResult
import com.siroca.domain.repository.BaseRepository
import com.siroca.remote.impl.PutResultImpl

/**
 * Created by azamat  on 8/18/19.
 */
object GetBasModule {
    val module = Kodein.Module {
        bind<BaseRemote<BaseModel>>() with provider { PutResultImpl(instance(), instance()) }
        bind<BaseRepository<BaseEntity>>() with provider { SpendResultRepository(instance(), instance()) }
        bind<PutSpendResult>() with provider { PutSpendResult(instance(), instance()) }
    }
}