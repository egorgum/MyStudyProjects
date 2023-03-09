fun main(){
    var firstName = "Egor"
    var lastName = "Gumenyuk"
    var height: Double = 1.93
    var weight: Float = 95f
    var isChild: Boolean = height < 1.5 || weight < 40f
    var info = "First Name - $firstName\nLastName - $lastName\nHeight - $height\nWeight - $weight\nI am a child - $isChild"
    println(info)
    println()
    height = 1.3
    isChild = height < 1.5 || weight < 40f
    info = "First Name - $firstName\nLastName - $lastName\nHeight - $height\nWeight - $weight\nI am a child - $isChild"
    println(info)
}