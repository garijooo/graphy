package kz.garijooo.graphy.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var points: MutableLiveData<MutableList<Float>>? = MutableLiveData(mutableListOf())

    var startOX: MutableLiveData<Float>? = MutableLiveData<Float>(null)
    var endOX: MutableLiveData<Float>? = MutableLiveData<Float>(null)
    var startOY: MutableLiveData<Float>? = MutableLiveData<Float>(null)
    var endOY: MutableLiveData<Float>? = MutableLiveData<Float>(null)

    var strokeWidth: MutableLiveData<Float>? = MutableLiveData<Float>(null)

    var graphColor: MutableLiveData<String>? = MutableLiveData<String>(null)

    var currentFunction: MutableLiveData<Int>? = MutableLiveData<Int>(null)
    var lastFunction: MutableLiveData<Int>? = MutableLiveData<Int>(null)
}