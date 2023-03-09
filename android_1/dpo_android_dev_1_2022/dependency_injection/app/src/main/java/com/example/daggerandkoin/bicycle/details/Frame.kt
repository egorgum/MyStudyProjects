package com.example.daggerandkoin.bicycle.details

class Frame(val serial: Int,val color: Int = ColorFrame.values().toList().shuffled().first().rgb)