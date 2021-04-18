package kz.garijooo.graphy.models

import android.util.Log

class ConverterModel(oxStart: Float, oxEnd: Float, width: Float) {
    private var _oxStart: Float
    var oxStart: Float
        get() = _oxStart
        set(value) { _oxStart = value }

    private var _oxEnd: Float
    var oxEnd: Float
        get() = _oxEnd
        set(value) { _oxEnd = value }

    private var _width: Float
    var width: Float
        get() = _width
        set(value) { _width = value }


    init{
        this._oxStart = oxStart
        this._oxEnd = oxEnd
        this._width = width
    }
    fun toCartesianOX(value: Float){

    }
    fun toCartesianOY(value: Float){

    }
    fun toDpOX(value: Float): Float{
        var result: Float = (this._width!! / (this._oxEnd!! - this._oxStart!!)) * value
        Log.d("555", result.toString())
        return result
    }
//    fun toDpOY(value: Float){
//        ((this.height / (this.oyEnd - this.oyStart)) * value)
//    }
}