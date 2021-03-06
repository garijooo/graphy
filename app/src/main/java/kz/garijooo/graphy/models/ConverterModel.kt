package kz.garijooo.graphy.models
class ConverterModel(oxStart: Float, oxEnd: Float, width: Float, oyStart: Float, oyEnd: Float, height: Float) {
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

    private var _oyStart: Float
    var oyStart: Float
        get() = _oyStart
        set(value) { _oyStart = value }

    private var _oyEnd: Float
    var oyEnd: Float
        get() = _oyEnd
        set(value) { _oyEnd = value }

    private var _height: Float
    var height: Float
        get() = _height
        set(value) { _height = value }

    init{
        this._oxStart = oxStart
        this._oxEnd = oxEnd
        this._width = width
        this._oyStart = oyStart
        this._oyEnd = oyEnd
        this._height = height
    }
    fun toCartesianOX(value: Float): Float{
        return (this.oxStart + (value * (this.oxEnd - this.oxStart) / this.width ))
    }
    fun toCartesianOY(value: Float){
        // not necessary to be implemented in the case of this project
    }
    fun toDpOX(value: Float): Float{
        return (((this.width / (this.oxEnd - this.oxStart)) * value))
    }
    fun toDpOY(value: Float): Float{
        return (((this.height) - ((value - this.oyStart) * this.height / (this.oyEnd - this.oyStart))))
    }
}
