package com.siroca.testpoint.injection

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.siroca.data.mapper.BaseMapper
import com.siroca.remote.mapper.BaseEntityMapper

/**
 * Created by azamat  on 8/18/19.
 */
object MapperModule {
    val module = Kodein.Module{
        bind<BaseMapper>() with provider { BaseMapper() }
        bind<BaseEntityMapper>() with provider { BaseEntityMapper() }
    }
}