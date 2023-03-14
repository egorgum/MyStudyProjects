package com.example.daggerandkoin.bicycle.factorys

import com.example.daggerandkoin.bicycle.details.Wheel
import kotlin.random.Random
import kotlin.random.nextInt

class WheelDealer {
    fun getWheel(): Wheel {
        return Wheel(serial = Random.nextInt(1..100))
    }
}