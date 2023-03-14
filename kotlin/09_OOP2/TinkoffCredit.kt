class TinCredit(balance:Int = 0): CreditCard(balance) {
    private var savings = 0.0
     override fun topUp(n: Int){
         balance += n
         savings += n * 0.00005
         println("Replenishment of the balance for $n rubles.")
     }
    override fun allInfo() {
        println("Your balance is $balance rubles. Credit limit is $creditLimit. Credit is $credit. Your savings are $savings")
    }
}