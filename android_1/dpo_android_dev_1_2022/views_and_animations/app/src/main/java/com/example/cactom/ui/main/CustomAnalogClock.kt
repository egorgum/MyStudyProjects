package com.example.cactom.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomAnalogClock: View {


    private var mHeight = 0
    private  var mWidth:Int = 0
    private val mClockHours = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mPadding = 0
    private val mNumeralSpacing = 0
    private var mHandTruncation = 0
    private  var mHourHandTruncation:Int = 0
    private var mRadius = 0
    private var mPaint: Paint? = null
    private val mRect: Rect = Rect()
    private var isInit = false
    var hours = 0
    var minutes = 0
    var seconds = 0

    constructor(context: Context?):super(context)

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):super (context, attrs, defStyleAttr)


    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50
            val minAttr = min(mHeight,mWidth)
            mRadius = minAttr / 2 - mPadding
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            isInit = true
        }
        canvas!!.drawColor(Color.DKGRAY)

        mPaint?.reset()
        mPaint?.color = Color.WHITE
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = 4F
        mPaint?.isAntiAlias = true
        canvas.drawCircle(
            (mWidth / 2).toFloat(), (mHeight / 2).toFloat(),(mRadius + mPadding - 10).toFloat(),mPaint!!)
        canvas.drawCircle(
            (mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 12F, mPaint!!)

        val fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14F, resources.displayMetrics)
        mPaint!!.textSize = fontSize

        for (hour in mClockHours) {
            val tmp: String = mClockHours[hour - 1].toString()
            mPaint!!.getTextBounds(tmp, 0, tmp.length, mRect)

            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + cos(angle) * mRadius - mRect.width() / 2)
            val y = (mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2)
            canvas.drawText(tmp, x.toFloat(), y.toFloat(), mPaint!!)
        }

        start(canvas, hours, minutes, seconds)
        postInvalidateDelayed(500)
        invalidate()

    }
    private fun drawHandLine(canvas: Canvas, moment: Int, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint!!.color = Color.YELLOW
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }
    private fun start(canvas: Canvas, h: Int, m: Int, s: Int) {
        var hour: Int = h
        hour = if (hour > 12) hour - 12 else hour
        drawHandLine(
            canvas,
            (hour + m /  60) * 5,
            isHour = true,
            isSecond = false
        ) // draw hours

        drawHandLine(
            canvas, m,
            isHour = false,
            isSecond = false
        ) // draw minutes

        drawHandLine(
            canvas, s,
            isHour = false,
            isSecond = true
        ) // draw seconds
    }

}
