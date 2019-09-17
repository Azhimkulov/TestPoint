package com.siroca.testpoint.injection

import android.os.Handler
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.siroca.domain.executor.PostExecutionThread
import com.siroca.presentation.presenter.EnergyPresenter
import com.siroca.remote.BaseService
import com.siroca.remote.BaseServiceFactory
import com.siroca.testpoint.BuildConfig
import com.siroca.testpoint.UiThread
import com.siroca.testpoint.utils.DialogUtils

/**
 * Created by azamat  on 8/13/19.
 */
object InjectionModule {
    val module = Kodein.Module {
        bind<PostExecutionThread>() with provider { UiThread() }
        bind<BaseService>() with provider { BaseServiceFactory.makeBaseService(BuildConfig.DEBUG) }

        bind<DialogUtils>() with provider { DialogUtils(instance()) }
        bind<EnergyPresenter>() with provider { EnergyPresenter(instance(), instance()) }
        bind<Handler>() with provider { Handler() }
    }
}