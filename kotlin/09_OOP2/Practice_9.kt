fun main(){
    val tin =  TinCredit(10000)
    val sbr =  SbrDebit(10000)
    val alfa = AlfaDebit(10000)

    tin.allInfo()
    tin.pay(1000)
    tin.allInfo()
    tin.topUp(6000)
    tin.info()
    tin.allInfo()
println()
    sbr.allInfo()
    sbr.pay(5000)
    sbr.pay(100000)
    sbr.topUp(4000)
    sbr.info()
    sbr.allInfo()
println()
    alfa.allInfo()
    alfa.pay(5000)
    alfa.topUp(5000)
    alfa.info()
    alfa.allInfo()
}