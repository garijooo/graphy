package kz.garijooo.graphy.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CartesianView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    constructor(context: Context) : this(context, null)
    val painterBG: Paint = Paint();

    init {

        painterBG.color = 0xfffaf18e.toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPaint(painterBG)
        }
    }
}