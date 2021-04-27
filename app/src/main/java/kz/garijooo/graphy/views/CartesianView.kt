package kz.garijooo.graphy.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kz.garijooo.graphy.models.ConverterModel

class CartesianView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    constructor(context: Context) : this(context, null)
    // painters
    val painterBG: Paint = Paint()
    val painterOX: Paint = Paint()
    val painterOXserifs: Paint = Paint()
    val painterGraph: Paint = Paint()

    // converter object
    private var _converter: ConverterModel? = null
    var converter: ConverterModel?
        get() = _converter
        set(value) { this._converter = value }

    // points
    private var points: MutableList<Float> = mutableListOf<Float>()
    fun updatePoints(points: MutableList<Float>) {
        this.points = points
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
            //drawLine(100.0F, (this@CartesianView.height.toFloat() / 2), (this@CartesianView.width.toFloat()), (this@CartesianView.height.toFloat() / 2), painterOX )
            var start: Int = startOX!!.toInt()
            var end: Int = endOX!!.toInt()
            for(i in start..end) {
                //drawLine(i.toFloat(), (this@CartesianView.height.toFloat() / 2 - 10F), i.toFloat(), (this@CartesianView.height.toFloat() / 2 + 10F), painterOXserifs)

            }
//            if(this@CartesianView.startOY!! < 0F && this@CartesianView.endOY!! > 0F) {
//               // drawLine()
////                this@CartesianView.converter.toDpOX()
//            }
            if(startOX != null) {
                if(startOX!! <= 0 && endOX!! >= 0) {

                    drawLine(converter!!.toDpOX( 0F - startOX!!), 0F, converter!!.toDpOX(0F - startOX!!), this@CartesianView.height.toFloat(), painterOX)
                }
            }
            if(startOY != null) {
                if(startOY!! <= 0 && endOY!! >= 0) {

                   // var value = converter?.toDpOX( -1 * startOY!!)!! - this@CartesianView.height.toFloat()

                    var value: Float? = converter?.toDpOY(0F);
                    Log.d("VALUE oy", value.toString())
                    drawLine(0F, value!!, this@CartesianView.width.toFloat(), value!!, painterOX)
                }
            }



        }
    }
    fun drawGraph(canvas: Canvas?) {
        canvas?.apply {
            val size: Int = this@CartesianView.points.size

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
        }
    }
}