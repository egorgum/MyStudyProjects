package com.example.daggerandkoin.bicycle

import android.os.Bundle
import com.example.daggerandkoin.MainActivity
import com.example.daggerandkoin.bicycle.factorys.BicycleFactory
import com.example.daggerandkoin.bicycle.factorys.FrameFactory
import com.example.daggerandkoin.bicycle.factorys.WheelDealer
import dagger.Component
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Singleton
@Component(modules = [DaggerModule::class])
interface DaggerComponent{
    fun bicycleFactory(): BicycleFactory
}


@Module
class DaggerModule(){
    @Provides
    @Singleton
    fun provideWheelDealer(): WheelDealer{
        return WheelDealer()
    }

    @Provides
    fun provideFrameFactory(): FrameFactory{
        return FrameFactory()
    }

    @Provides
    fun provideBicycleFactory(wheelDealer: WheelDealer, factory: FrameFactory):BicycleFactory{
        return BicycleFactory(wheelDealer = wheelDealer, frameFactory = factory)
    }
}