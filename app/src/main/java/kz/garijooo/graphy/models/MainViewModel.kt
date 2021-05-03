package kz.garijooo.graphy.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var limitations: MutableLiveData<List<Float>> = MutableLiveData(mutableListOf())

//    private var _data = MutableLiveData<Float>()
//    var data: MutableLiveData<Float>
//        get() = _data
//        set(value) {
//            _data = value
//        }
    var startOX: MutableLiveData<Float>? = MutableLiveData<Float>()
    var endOX: MutableLiveData<Float>? = MutableLiveData<Float>()
    var startOY: MutableLiveData<Float>? = MutableLiveData<Float>()
    var endOY: MutableLiveData<Float>? = MutableLiveData<Float>()

    var strokeWidth: MutableLiveData<Float>? = MutableLiveData<Float>()

    var graphColor: MutableLiveData<Int>? = MutableLiveData<Int>()
}