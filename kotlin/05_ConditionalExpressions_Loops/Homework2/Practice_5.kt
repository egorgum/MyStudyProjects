package Homework2

fun main(){
    print("Enter a number: ")
    var m = readLine()?.toIntOrNull()?: return
    while (m <= 0){
        println("Input error")
        print("Enter a number: ")
        m = readLine()?.toIntOrNull()?: return
    }
    println("At the position \"$m\" - the number \"${fibonacci(m)}\"")
}


fun fibonacci(n: Int):Long{
    var a: Long = 0
    var b: Long = 1
    var i = 1
    while (i != n){
        val c: Long = a + b
        a = b
        b = c
        i++
    }
    return a
}