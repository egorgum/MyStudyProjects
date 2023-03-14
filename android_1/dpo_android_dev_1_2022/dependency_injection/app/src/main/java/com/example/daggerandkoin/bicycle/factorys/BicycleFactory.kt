package com.example.daggerandkoin.bicycle.factorys

import com.example.daggerandkoin.bicycle.Bicycle
import com.example.daggerandkoin.bicycle.details.ColorFrame
import com.example.daggerandkoin.bicycle.details.Logo
import java.util.stream.Collectors.toList
import javax.inject.Inject
import kotlin.random.Random

class BicycleFactory constructor(
    val frameFactory: FrameFactory,
    val wheelDealer: WheelDealer
){

    fun build (): Bicycle{
           return Bicycle(wheel = wheelDealer.getWheel(), frame = frameFactory.getFrame(), logo = Logo.values().toList().shuffled().first())
   }
}