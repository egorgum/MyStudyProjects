abstract class BankCard{
    abstract var balance: Int
    //Функция пополнения
    open fun topUp(n: Int){
        balance += n
        println("Replenishment of the balance for $n rubles.")
    }
    //Функция оплаты
    abstract fun pay(n: Int):Boolean
    //Информация о балансе
    fun info(){
        println("Your balance is $balance rubles")
    }
    //Информация о балансе, кредитном лимите и любых других средствах.

    open fun allInfo(){
        println("Your balance is $balance rubles.")
    }

}