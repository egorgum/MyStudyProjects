package com.example.myapplication

sealed class Routes (val route: String){
    object Main : Routes("main")
    object Person: Routes("person")
}