fun main(){
    USD().convertToRub(110.0)
    EUR().convertToRub((110.0))
    Converters.get("USD").convertToRub(110.0)
    Converters.get("EUR").convertToRub(110.0)
    Converters.get("ENG").convertToRub(1000.0)
}
