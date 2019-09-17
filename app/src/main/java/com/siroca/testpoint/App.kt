package com.siroca.testpoint

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.siroca.testpoint.injection.GetBasModule
import com.siroca.testpoint.injection.InjectionModule
import com.siroca.testpoint.injection.MapperModule

/**
 * Created by azamat  on 9/15/19.
 */
class App: Application(), KodeinAware {
    override val kodein = Kodein {
        import(GetBasModule.module)
        import(InjectionModule.module)
        import(MapperModule.module)
    }
}
