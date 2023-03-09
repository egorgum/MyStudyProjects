object Converters {
    fun get(currencyCode: String):CurrencyConverter {
        if (currencyCode == "USD") return USD()
        return if (currencyCode == "EUR") EUR()
        else return convert
    }

    private val convert = object: CurrencyConverter {
        override val currencyCode: String
            get() = "Unknown currency"
        override fun convertToRub(rub: Double) {
            print("Enter the ratio: ")
            val m = readLine()?.toDouble()
            println("$rub rubles = ${rub * m!!} $currencyCode")
        }
    }
}

