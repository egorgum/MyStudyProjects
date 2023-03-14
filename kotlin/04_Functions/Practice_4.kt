fun main() {
    val str = "F2p)v\"y233{0->c}ttelciFc"
    var first = str.substring(0,(str.length / 2).toInt())
    var second = str.substring((str.length / 2).toInt(),str.length)
    var word = decodeOne(first) + decodeTwo(second)
    println("Encrypted message - \"$str\"\nDecrypted message - \"$word\"\n")
}


fun decodeOne(code: String): String {
    //Действия с перовой половиной
    var firstStr = code.map {char -> char + 1}.joinToString("")
    firstStr = firstStr.replace("5", "s")
    firstStr = firstStr.replace("4", "u")
    firstStr = firstStr.map {char -> char - 3}.joinToString("")
    firstStr = firstStr.replace("0", "o")
    return firstStr
}


fun decodeTwo(code: String): String {
    //Действия со второй половиной
    var secondStr = code.reversed()
    secondStr = secondStr.map {char -> char - 4}.joinToString("")
    secondStr =  secondStr.replace("_"," ")
    return secondStr
}
