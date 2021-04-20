package kz.garijooo.graphy.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CartesianView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    constructor(context: Context) : this(context, null)
    val painterBG: Paint = Paint()
    val painterOX: Paint = Paint()
    val painterOXserifs: Paint = Paint()
    val painterGraph: Paint = Paint()

    // points
    private var points: MutableList<Float> = mutableListOf<Float>()
    fun updatePoints(points: MutableList<Float>) {
        this.points = points
        Log.d("UPDATE POINTS", "updated")
        Log.d("updated points", points.toString())
        invalidate()
    }
    // numeric fields
    private var _axisOX: Float? = null

    private var _startOX: Float? = null
    private var _endOX: Float? = null

    private var _startOY: Float? = null
    private var _endOY: Float? = null

    //private var _width: Float? = null
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

        painterGraph.strokeWidth = 5F
        painterGraph.color = 0xff7a0000.toInt()
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
    fun drawGraph(canvas: Canvas?) {
        canvas?.apply {
            val size: Int = this@CartesianView.points.size
            Log.d("points", this@CartesianView.points.toString())
            if(size != 0) {
                for(i in 0..(size - 2)){
                    drawLine(i.toFloat(), this@CartesianView.points[i], (i+1).toFloat(), this@CartesianView.points[i+1], painterGraph)
//                    Log.d(i.toString(), this@CartesianView.points[i].toString())
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPaint(painterBG)
            drawAxis(this)
            drawGraph(this)
            Log.d("ON DRAW", "has been drown")
        }
    }
}