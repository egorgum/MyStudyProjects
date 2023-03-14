fun main() {
    val str = "F2p)v\"y233{0->c}ttelciFc"
    println("Encrypted message - \"$str\"\nDecrypted message - \"${decoder(str)}\"\n")
}


fun decoder(code: String): String{
    //Деление на пополам
    var first = code.substring(0,(code.length / 2).toInt())
    var second = code.substring((code.length / 2).toInt(),code.length)


    //Действия с перовой половиной
    var firstStr =  first.map {char -> char + 1}.joinToString("")
    firstStr =  firstStr.replace("5","s")
    firstStr =  firstStr.replace("4","u")
    firstStr =  firstStr.map {char -> char - 3}.joinToString("")
    firstStr =  firstStr.replace("0","o")


    //Действия со второй половиной
    var secondStr = second.reversed()
    secondStr = secondStr.map {char -> char - 4}.joinToString("")
    secondStr =  secondStr.replace("_"," ")


    return firstStr + secondStr
}
