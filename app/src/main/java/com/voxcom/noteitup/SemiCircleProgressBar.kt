package com.voxcom.noteitup

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class SemiCircleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var progress: Float = 0f  // value between 0 and 100
    private val paintBackground = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        strokeWidth = 25f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val paintForeground = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 25f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 60f
        textAlign = Paint.Align.CENTER
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = width / 2 - 30f
        val centerX = width / 2
        val centerY = height

        val oval = RectF(
            centerX - radius,
            centerY - 2 * radius,
            centerX + radius,
            centerY
        )

        // Background arc
        canvas.drawArc(oval, 180f, 180f, false, paintBackground)

        // Foreground arc (progress)
        val sweepAngle = (progress / 100f) * 180f
        canvas.drawArc(oval, 180f, sweepAngle, false, paintForeground)

        // Draw text
        canvas.drawText("${progress.toInt()}%", centerX, centerY - radius, paintText)
    }
}