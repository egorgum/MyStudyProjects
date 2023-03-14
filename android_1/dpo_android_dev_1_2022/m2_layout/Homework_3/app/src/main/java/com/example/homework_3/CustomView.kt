package com.example.homework_3
import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.homework_3.databinding.MyCustomViewBinding

class CustomView
@JvmOverloads   constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs,defStyleAttr){
    val myView = MyCustomViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(myView.root)
    }
}


