fun main(){
    //Ввод количества номеров с проверкой на положительное число
    print("Enter a number of phone numbers: ")
    var n: Int = readLine()?.toIntOrNull()?: return
    while (n <= 0){
        println("Input error")
        print("Enter a number of phone numbers: ")
        n = readLine()?.toIntOrNull()?: return
    }
    //Основное тело программы
    val numbers = mutableListOf<String>()
    println()
    val listOfNumbers = fillingList(numbers, n)
    println("\nPhone numbers with +7 - ${listOfNumbers.filter{it.startsWith("+7")}}")
    println("The size of the set of unique phone numbers ${listOfNumbers.toSet().size}")
    println("The sum of unique phone numbers ${listOfNumbers.toSet().sumOf { it.length }}")
    println()
    val result = fillingMap(listOfNumbers.toSet())
    println()
    result.forEach { item ->
        println("Subscriber: ${item.value}. Phone number: ${item.key}")
    }
    //Дополнительное задание 1
    println("\nSorted map by phone numbers")
    val sortedMap = result.toSortedMap()
        sortedMap.forEach{
            println("Subscriber: ${it.value}. Phone number: ${it.key}")}
    //Дополнительное задание 2
    println("\nSorted map by subscribers")
    sortedMap.entries.sortedBy { it.value }.forEach{
        println("Subscriber: ${it.value}. Phone number: ${it.key}")}
}
//Функция заполнения номеров
fun fillingList(list: MutableList<String>, n: Int): List<String>{
    for (index in 0 until n){
        print("Enter the phone number: ")
        list.add(readLine().toString())
    }
    return list
}
//Функция заполнения абонентов к номерам
fun fillingMap(set: Set<String>): Map<String, String>{
    val mapOfNumbers = mutableMapOf<String, String>()
    for (item in set.indices){
        print("Enter the name of the person with the phone number: ${set.elementAt(item)} ")
        mapOfNumbers[set.elementAt(item)] = readLine().toString()
    }
    return mapOfNumbers
}