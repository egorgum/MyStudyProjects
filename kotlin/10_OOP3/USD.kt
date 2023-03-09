class USD: CurrencyConverter {
    override val currencyCode: String = "USD"

    override fun convertToRub(rub: Double){
        println("$rub rubles = ${rub * 0.016606} $currencyCode")
    }
}
