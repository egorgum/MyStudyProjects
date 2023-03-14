package com.example.daggerandkoin

import android.app.Application
import com.example.daggerandkoin.bicycle.DaggerComponent
import com.example.daggerandkoin.bicycle.DaggerDaggerComponent
import com.example.daggerandkoin.bicycle.factorys.BicycleFactory
import com.example.daggerandkoin.bicycle.factorys.FrameFactory
import com.example.daggerandkoin.bicycle.factorys.WheelDealer
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.module

class App: Application() {
    var mainComponent: DaggerComponent = DaggerDaggerComponent.create()
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                module{
                    single{ WheelDealer()}
                    factory {FrameFactory()}
                    factory {BicycleFactory(get(), get())}

                }
            )
        }
    }
}