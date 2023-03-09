class AlfaDebit(balance: Int = 0):DebitCard(balance) {
    private var bonus = 0.0

    override fun pay(n: Int):Boolean{
        if (balance >= n){
            balance -= n
            println("A purchase was made for $n rubles.")
            bonus += n / 1000
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
    override fun allInfo() {
        println("Your balance is $balance rubles. Your bonus is $bonus")
    }
}