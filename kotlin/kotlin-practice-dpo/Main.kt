fun main(){
    print("Enter a number of phone numbers: ")
    var n: Int = readLine()?.toIntOrNull()?: return
    while (n <= 0){
        println("Input error")
        print("Enter a number of phone numbers: ")
        n = readLine()?.toIntOrNull()?: return
    }
    val numbers = mutableListOf<String>()
    val realNumbers = fillingList(numbers, n)

    println("\nPhone numbers with +7 - ${realNumbers.filter { it.startsWith("+7") }}")
    println("\nUnique numbers - ${realNumbers.toSet()}")
    println("\nSum of numbers - ${realNumbers.sumOf {it.length }}")

    val nMup = fillingMap(realNumbers.toSet())
    nMup.forEach{ (key, value) -> println("Person: $value. Number: $key") }
}

fun fillingList(list: MutableList<String>, n: Int): List<String>{
    for (index in 0 until n){
        print("Enter the phone number: ")
        list.add(readLine().toString())
    }
    return list
}

fun fillingMap(set: Set<String>): Map<String,String>{
    val mup = mutableMapOf<String,String>()
        for (i in set.indices){
            print("Enter the name of the person with the phone number: ${set.elementAt(i)} ")
            mup[set.elementAt(i)] = readLine().toString()
        }
    return mup

}