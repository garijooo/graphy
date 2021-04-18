package kz.garijooo.graphy.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CartesianView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    constructor(context: Context) : this(context, null)
    val painterBG: Paint = Paint()
    val painterOX: Paint = Paint()
    val painterOXserifs: Paint = Paint()

    // numeric fields
    private var _axisOX: Float? = null

    private var _startOX: Float? = null
    private var _endOX: Float? = null

    private var _startOY: Float? = null
    private var _endOY: Float? = null

    private var _width: Float? = null
    // getters & setters
    var axisOX: Float?
        get() = _axisOX
        set(value) { _axisOX = value }

    var startOX: Float?
        get() = _startOX
        set(value) { _startOX = value }
    var endOX: Float?
        get() = _endOX
        set(value) { _endOX = value }

    var startOY: Float?
        get() = _startOY
        set(value) { _startOY = value }
    var endOY: Float?
        get() = _endOY
        set(value) { _endOY = value }

//    var width: Float?
//        get() = _width
//        set(value) { _width = value }

    init {
        painterBG.color = 0xfffaf18e.toInt()
        painterOX.color = 0xff000000.toInt()
        painterOX.strokeWidth = 10.0F
        painterOXserifs.strokeWidth = 7.5F
    }

    fun drawAxis(canvas: Canvas?) {
        canvas?.apply {
            drawLine(100.0F, (this@CartesianView.height.toFloat() / 2), (this@CartesianView.width.toFloat()), (this@CartesianView.height.toFloat() / 2), painterOX )
            var start: Int = startOX!!.toInt()
            var end: Int = endOX!!.toInt()
            for(i in start..end) {
                //drawLine(i.toFloat(), (this@CartesianView.height.toFloat() / 2 - 10F), i.toFloat(), (this@CartesianView.height.toFloat() / 2 + 10F), painterOXserifs)

            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPaint(painterBG)
            drawAxis(this)
        }
    }
}