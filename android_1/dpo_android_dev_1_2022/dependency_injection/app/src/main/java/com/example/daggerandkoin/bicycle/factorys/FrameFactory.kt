package com.example.daggerandkoin.bicycle.factorys

import com.example.daggerandkoin.bicycle.details.Frame
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class FrameFactory(
){
    fun getFrame(): Frame {
        return Frame(serial = Random.nextInt(101..200))
    }
}