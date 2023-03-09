open class CreditCard(override var balance: Int = 0): BankCard() {
    val creditLimit = 1000
    var credit = 0

    override fun topUp(n: Int) {
        if (credit == 0){
        super.topUp(n)
        }
        if (credit > 0){
            credit -= n
            if (credit < 0){
                super.topUp(-(credit))
                credit = 0
            }
        }
    }

    override fun pay(n: Int): Boolean {
        if (balance >= n){
            balance -= n
            println("A purchase was made for $n rubles.")
            return true
        }
        if (balance < n && creditLimit >= n - balance) {
            credit = n - balance
            balance = 0
            println("A purchase was made for $n rubles.")
            return true
        }
        return if (balance <n && creditLimit < n - balance){
            println("Insufficient funds on the card")
            false
        } else{
            println("Error")
            false
        }
    }

    override fun allInfo() {
        println("Your balance is $balance rubles. Credit limit is $creditLimit. Credit is $credit.")
    }
}