open class DebitCard(override var balance: Int = 0) :BankCard() {
    override fun pay(n: Int):Boolean{
        if (balance >= n){
            balance -= n
            println("A purchase was made for $n rubles. ${info()}.")
            return true
        }
        return if (balance < n) {
            println("Insufficient funds on the card")
            false
        } else{
            println("Error")
            false
        }
    }

}