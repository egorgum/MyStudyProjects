class Stack <T>{
    var stack = mutableListOf<T>()

    fun push(item: T){
        stack.add(item)
    }

    fun isEmpty():Boolean{
        return stack.size == 0
    }

   fun pop(): T?{
       return when (stack.isEmpty()){
           true -> null
           false -> stack.removeAt(stack.lastIndex)
       }
    }

    override fun toString(): String {
        return "$stack"
    }
}


