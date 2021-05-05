package kz.garijooo.graphy.controllers

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import kz.garijooo.graphy.R
import kz.garijooo.graphy.models.Constants
import kz.garijooo.graphy.models.ConverterModel
import kz.garijooo.graphy.models.MainViewModel
import kz.garijooo.graphy.views.CartesianView

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel
    private var visibilityBtn: Button? = null
    private var cartesianSystem: CartesianView? = null
    // main layout
    private var mainLayout: ConstraintLayout? = null

    // editTexts
    private var editTextStartOX: EditText? = null
    private var editTextEndOX: EditText? = null
    private var editTextStartOY: EditText? = null
    private var editTextEndOY: EditText? = null
    // stroke width
    private var strokeWidthEditText: EditText? = null
    // graph color
    private var graphColor: Spinner? = null
    // limitations
    private var startOX: Float? = null
    private var endOX: Float? = null
    private var startOY: Float? = null
    private var endOY: Float? = null
    // sub elements
    private var errorTextView: TextView? = null
    // model objects
    private lateinit var converter: ConverterModel

    private var currentColor: String? = null
    // color items
    var items: Array<String> = arrayOf<String>("green", "red", "blue", "yellow")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        cartesianSystem = activity?.findViewById<CartesianView>(R.id.cartesian)


        graphColor = activity?.findViewById<Spinner>(R.id.graph_color)?.apply {
            var adapter: ArrayAdapter<String> =  ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item, items)
            this.adapter = adapter
            var index: Int? = null
            for(i in 0 until items.size)
                if(viewModel.graphColor!!.value != null) {
                    if (viewModel.graphColor!!.value == items[i]) {
                        index = i
                    }
                }
            this.setSelection(index?:0)

            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    cartesianSystem?.changeColor(parent.selectedItem.toString())
                    currentColor = parent.selectedItem.toString()
                    viewModel.graphColor?.postValue(currentColor)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        editTextStartOX = activity?.findViewById<EditText>(R.id.ox_start)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextStartOX?.text.toString() == "" || editTextStartOX?.text.toString() == "-") viewModel.startOX?.postValue(0.0F)
                    else {
                        viewModel.startOX?.postValue(editTextStartOX?.text.toString().toFloat())
                        viewModel.points?.postValue(null)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
        }

        editTextEndOX = activity?.findViewById<EditText>(R.id.ox_end)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextEndOX?.text.toString() == "" || editTextEndOX?.text.toString() == "-") viewModel.endOX?.postValue(0.0F)
                    else {
                        viewModel.endOX?.postValue(editTextEndOX?.text.toString().toFloat())
                        viewModel.points?.postValue(null)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
        }

        editTextStartOY = activity?.findViewById<EditText>(R.id.oy_start)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextStartOY?.text.toString() == "" || editTextStartOY?.text.toString() == "-") viewModel.startOY?.postValue(0.0F)
                    else {
                        viewModel.startOY?.postValue(editTextStartOY?.text.toString().toFloat())
                        viewModel.points?.postValue(null)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
        }

        editTextEndOY = activity?.findViewById<EditText>(R.id.oy_end)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextEndOY?.text.toString() == "" || editTextEndOY?.text.toString() == "-") viewModel.endOY?.postValue(0.0F)
                    else {
                        viewModel.endOY?.postValue(editTextEndOY?.text.toString().toFloat())
                        viewModel.points?.postValue(null)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
        }

        strokeWidthEditText = activity?.findViewById<EditText>(R.id.stroke_width)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(strokeWidthEditText?.text.toString() == "") viewModel.strokeWidth?.postValue(2F)
                    else viewModel.strokeWidth?.postValue(strokeWidthEditText?.text.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
        }

        errorTextView = activity?.findViewById<TextView>(R.id.error_textView)?.apply {
            this.visibility = View.INVISIBLE
        }

        visibilityBtn = activity?.findViewById<Button>(R.id.visibilityBtn)

        visibilityBtn?.setOnClickListener {
            if(viewModel.startOX!!.value != null && viewModel.endOX!!.value != null){
                if(viewModel.startOX!!.value!! < viewModel.endOX!!.value!!) {
                    errorTextView?.visibility = View.INVISIBLE
                    if(visibilityBtn?.text.toString() == getString(R.string.btn_hide)) {
                        visibilityBtn?.text = getString(R.string.btn_show)
                        cartesianSystem?.visibility = View.INVISIBLE
                    }
                    else {
                        Log.d("V PIVE", "YA V PIVE")
                        // hide btn and cartesian view visualisation
                        visibilityBtn?.visibility = View.VISIBLE
                        visibilityBtn?.text = getString(R.string.btn_hide)
                        cartesianSystem?.visibility = View.VISIBLE

                        // converter initialization
                        converter = ConverterModel(viewModel.startOX!!.value!!, viewModel.endOX!!.value!!, cartesianSystem!!.width.toFloat(), viewModel.startOY!!.value!!, viewModel.endOY!!.value!!, cartesianSystem!!.height.toFloat())

                        cartesianSystem?.converter = converter;
                        cartesianSystem?.startOX = viewModel.startOX!!.value!!
                        cartesianSystem?.endOX = viewModel.endOX!!.value!!
                        cartesianSystem?.startOY = viewModel.startOY!!.value!!
                        cartesianSystem?.endOY = viewModel.endOY!!.value!!

                        // points list calculation
                        if(viewModel.points?.value == null || viewModel.points?.value.isNullOrEmpty()) {
                            var points: MutableList<Float> = mutableListOf<Float>()
                            var size: Int = cartesianSystem?.width?:1
                            for(i in 0 until size) points.add(converter.toDpOY(this.func(converter.toCartesianOX(i.toFloat()))))
                            viewModel.points?.postValue(points)
                            Log.d("points", points.toString())
                            cartesianSystem?.updateGraph(points, viewModel.strokeWidth!!.value!!, viewModel.graphColor!!.value!!)
                        }
                        else if(viewModel.points?.value != null) cartesianSystem?.updateGraph(viewModel.points!!.value!!, viewModel.strokeWidth!!.value!!, viewModel.graphColor!!.value!!)
                    }
                }
                else {
                    errorTextView?.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val prefs = activity?.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
        prefs?.run {
            // stroke width manipulations
            val strokeWidthVal = getFloat(Constants.STROKE_WIDTH, 5F)
            viewModel.strokeWidth?.postValue(strokeWidthVal)
            // visualisation
            if(strokeWidthVal.toString() != null)
                strokeWidthEditText?.setText(strokeWidthVal.toString() , TextView.BufferType.EDITABLE)

            // color manipulations
            val graphColorVal = getString(Constants.GRAPH_COLOR, "")
            viewModel.graphColor?.postValue(graphColorVal)
            // visualisation
            var index: Int? = null
            for(i in 0 until items.size)
                if(graphColorVal != null) {
                    if (graphColorVal == items[i]) {
                        index = i
                    }
                }
            graphColor?.setSelection(index?:0)

            // ox limitations
            val startOxVal = getFloat(Constants.START_OX, 0F)
            viewModel.startOX?.postValue(startOxVal)
            val endOxVal = getFloat(Constants.END_OX, 0F)
            viewModel.endOX?.postValue(endOxVal)
            // visualisation
            if(startOxVal.toString() != null)
                editTextStartOX?.setText(startOxVal.toString() , TextView.BufferType.EDITABLE)
            if(endOxVal.toString() != null)
                editTextEndOX?.setText(endOxVal.toString() , TextView.BufferType.EDITABLE)

            // oy limitations
            val startOyVal = getFloat(Constants.START_OY, 0F)
            viewModel.startOY?.postValue(startOyVal)
            val endOyVal = getFloat(Constants.END_OY, 0F)
            viewModel.endOY?.postValue(endOyVal)
            // visualisation
            if(startOyVal.toString() != null)
                editTextStartOY?.setText(startOyVal.toString() , TextView.BufferType.EDITABLE)
            if(endOyVal.toString() != null)
                editTextEndOY?.setText(endOyVal.toString() , TextView.BufferType.EDITABLE)

            // points
            val pointsList = MutableList(cartesianSystem?.width?:1){
                getFloat(Constants.POINTS+it.toString(), 0F)
            }
            Log.d("START POINTS_LIST", pointsList.toString())
            viewModel.points?.postValue(pointsList.toMutableList())

        }
    }

    override fun onStop() {
        super.onStop()
        val prefs = activity?.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
        prefs?.run {
            edit().run {
                putFloat(Constants.STROKE_WIDTH, viewModel.strokeWidth?.value?:5F)

                putFloat(Constants.START_OX, viewModel.startOX?.value?:0F)
                putFloat(Constants.END_OX, viewModel.endOX?.value?:0F)
                putFloat(Constants.START_OY, viewModel.startOY?.value?:0F)
                putFloat(Constants.END_OY, viewModel.endOY?.value?:0F)

                putString(Constants.GRAPH_COLOR, viewModel.graphColor!!.value?:"green")

                viewModel.points?.value?.forEachIndexed() { id, value ->
                    putFloat(Constants.POINTS + id.toString(), value)
                }

                apply()
            }
        }
    }

    fun func(x: Float): Float {
        return (1 / x)
    }
}
