class SbrDebit(balance: Int = 0):DebitCard(balance){
    private var cashBack = 0.0
    override fun pay(n: Int):Boolean{
        return if (balance >= n){
            balance -= n
            println("A purchase was made for $n rubles.")
            if (n >= 5000) cashBack += n / 20
            true
        } else {
            println("Insufficient funds on the card")
            false
        }
    }
    override fun allInfo() {
        println("Your balance is $balance rubles. Your cashback is $cashBack")
    }
}
