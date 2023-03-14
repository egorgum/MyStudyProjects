interface CurrencyConverter {
    val currencyCode: String
    fun convertToRub(rub: Double)
}