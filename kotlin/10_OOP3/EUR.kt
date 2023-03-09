class EUR:CurrencyConverter {
    override val currencyCode: String = "EUR"

    override fun convertToRub(rub: Double){
        println("$rub rubles = ${rub * 0.016393} $currencyCode")
    }
}