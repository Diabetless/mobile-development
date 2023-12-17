package com.ch2Ps073.diabetless.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
<<<<<<< HEAD
=======
import androidx.core.view.marginBottom
>>>>>>> chello
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

class PieView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    private var color: String = "#ef5350"
    private val rectF = RectF(0.0f, 0.0f, 0.0f, 0.0f)

    init {
        val density = context.resources.displayMetrics.density
        val strokeWidth = 14 * density     // To convert width in dp to pixels

        paint.isAntiAlias = true
        paint.isDither = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        val startTop = 0.0f + marginTop
<<<<<<< HEAD
=======
        val startBottom = 0.0f
>>>>>>> chello
        val startLeft = 0.0f + marginStart
        val endRight = width.toFloat() - marginEnd

        rectF.set(startLeft, startTop, endRight, endRight) // Creating a box

        var startPoint = 0f  // To start from top center point
        paint.color = Color.parseColor(color)
        canvas.drawArc(rectF, startPoint, 360f, false, paint)
    }

    fun setColor(colors: String) {
        color = colors
        invalidate()
    }
}