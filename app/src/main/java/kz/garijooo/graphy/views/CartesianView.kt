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
    val painterAxis: Paint = Paint()
    val painterAxisSerifs: Paint = Paint()
    val painterGraph: Paint = Paint()

    // converter object
    private var _converter: ConverterModel? = null
    var converter: ConverterModel?
        get() = _converter
        set(value) { this._converter = value }

    // points
    private var points: MutableList<Float> = mutableListOf<Float>()
    fun updateGraph(points: MutableList<Float>, strokeWidth: Float) {
        this.points = points
        this.painterGraph.strokeWidth = strokeWidth
        invalidate()
    }
    // numeric fields
    private var _axisOX: Float? = null

    private var _startOX: Float? = null
    private var _endOX: Float? = null

    private var _startOY: Float? = null
    private var _endOY: Float? = null

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

    init {
        painterBG.color = 0xfffaf18e.toInt()
        painterAxis.color = 0xff000000.toInt()
        painterAxis.strokeWidth = 7F
        painterAxisSerifs.strokeWidth = 3.5F

        painterGraph.strokeWidth = 2F
        painterGraph.color = 0xff66ac4a.toInt()
    }

    fun drawAxis(canvas: Canvas?) {
        canvas?.apply {
            var startOxInt: Int = startOX!!.toInt()
            var endOxInt: Int = endOX!!.toInt()
            var startOyInt: Int = startOY!!.toInt()
            var endOyInt: Int = endOY!!.toInt()

            if(startOX != null) {
                if(startOX!! <= 0 && endOX!! >= 0) {
                    drawLine(converter!!.toDpOX( 0F - startOX!!), 0F, converter!!.toDpOX(0F - startOX!!), this@CartesianView.height.toFloat(), painterAxis)
                    if(endOyInt - startOyInt <= 50) {
                        for(i in startOyInt..endOyInt) {
                            var valueOy: Float = converter?.toDpOY(i.toFloat())!!
                            var valueOx: Float = converter?.toDpOX(0F - startOX!!)!!
                            drawLine(valueOx + 20F, valueOy, valueOx - 20F, valueOy , painterAxisSerifs);
                        }
                    }
                    else if(endOyInt - startOyInt > 50 && endOyInt - startOyInt <= 100) {
                        for(i in startOyInt..endOyInt step 10) {
                            var valueOy: Float = converter?.toDpOY(i.toFloat())!!
                            var valueOx: Float = converter?.toDpOX(0F - startOX!!)!!
                            drawLine(valueOx + 20F, valueOy, valueOx - 20F, valueOy , painterAxisSerifs);
                        }
                    }
                }
            }
            if(startOY != null) {

                if(startOY!! <= 0 && endOY!! >= 0) {
                    drawLine(0F, converter?.toDpOY(0F)!!, this@CartesianView.width.toFloat(), converter?.toDpOY(0F)!!, painterAxis)
                    if(endOxInt - startOxInt <= 50){
                        for(i in startOxInt..endOxInt) {
                            drawLine(converter!!.toDpOX(i.toFloat() - startOX!!),converter!!.toDpOY(0F) + 20F, converter!!.toDpOX(i.toFloat() - startOX!!), converter!!.toDpOY(0F) - 20F, painterAxisSerifs)
                        }
                    }
                    else if(endOxInt - startOxInt > 50 && endOxInt - startOxInt <= 100) {
                        for(i in startOxInt..endOxInt step 10) {
                            drawLine(converter!!.toDpOX(i.toFloat() - startOX!!),converter!!.toDpOY(0F) + 20F, converter!!.toDpOX(i.toFloat() - startOX!!), converter!!.toDpOY(0F) - 20F, painterAxisSerifs)
                        }
                    }
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