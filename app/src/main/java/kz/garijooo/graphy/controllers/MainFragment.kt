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
    private var converter: ConverterModel = ConverterModel(0F, 0F, 0F, 0F, 0F, 0F)



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        cartesianSystem = activity?.findViewById<CartesianView>(R.id.cartesian)

        if(viewModel.graphColor?.value != null)
            Log.d("graph color", viewModel.graphColor?.value.toString())
        if(viewModel.strokeWidth?.value != null)
            Log.d("stroke width", viewModel.strokeWidth?.value.toString())

        graphColor = activity?.findViewById<Spinner>(R.id.graph_color)?.apply {
            var items: Array<String> = arrayOf<String>("green", "red", "blue", "yellow")
            var adapter: ArrayAdapter<String> =  ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item, items)
            this.adapter = adapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    cartesianSystem?.changeColor(parent.selectedItem.toString())
                    viewModel.graphColor?.postValue(parent.selectedItem.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        editTextStartOX = activity?.findViewById<EditText>(R.id.ox_start)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextStartOX?.text.toString() == "" || editTextStartOX?.text.toString() == "-") viewModel.startOX?.postValue(0.0F)
                    else viewModel.startOX?.postValue(editTextStartOX?.text.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
            if(viewModel.startOX != null) {
                Log.d("check", viewModel.startOX!!.toString())
//                this.setText(viewModel.startOX!!.toString())
            }
        }

        editTextEndOX = activity?.findViewById<EditText>(R.id.ox_end)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextEndOX?.text.toString() == "" || editTextEndOX?.text.toString() == "-") viewModel.endOX?.postValue(0.0F)
                    else viewModel.endOX?.postValue(editTextEndOX?.text.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                }
            })
           // if(viewModel.endOX != null) this.setText(viewModel.endOX!!.toString())
        }

        editTextStartOY = activity?.findViewById<EditText>(R.id.oy_start)?.apply {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(editTextStartOY?.text.toString() == "" || editTextStartOY?.text.toString() == "-") viewModel.startOY?.postValue(0.0F)
                    else viewModel.startOY?.postValue(editTextStartOY?.text.toString().toFloat())
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
                    else viewModel.endOY?.postValue(editTextEndOY?.text.toString().toFloat())
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
            viewModel.startOX?.observe(this@MainFragment, Observer {
                this.startOX = it
            })
            viewModel.endOX?.observe(this@MainFragment, Observer {
                this.endOX = it
            })
            viewModel.startOY?.observe(this@MainFragment, Observer {
                this.startOY = it
            })
            viewModel.endOY?.observe(this@MainFragment, Observer {
                this.endOY = it
            })

            if(this.startOX != null && this.endOX != null){
                if(this.startOX!! < this.endOX!!) {
                    errorTextView?.visibility = View.INVISIBLE
                    if(visibilityBtn?.text.toString() == getString(R.string.btn_hide)) {
                        visibilityBtn?.text = getString(R.string.btn_show)
                        cartesianSystem?.visibility = View.INVISIBLE
                    }
                    else {
                        visibilityBtn?.text = getString(R.string.btn_hide)
                        cartesianSystem?.visibility = View.VISIBLE

                        converter.oxStart = this.startOX!!
                        converter.oxEnd = this.endOX!!
                        converter.oyStart = this.startOY!!
                        converter.oyEnd = this.endOY!!
                        converter.width = cartesianSystem!!.width.toFloat()
                        converter.height = cartesianSystem!!.height.toFloat()

                        cartesianSystem?.converter = converter;
                        cartesianSystem?.startOX = this.startOX!!
                        cartesianSystem?.endOX = this.endOX!!
                        cartesianSystem?.startOY = this.startOY!!
                        cartesianSystem?.endOY = this.endOY!!
                        var points: MutableList<Float> = mutableListOf<Float>()
                        var size: Int = cartesianSystem?.width ?: 1

                        for(i in 0 until size){
                            points.add(converter.toDpOY(this.func(converter.toCartesianOX(i.toFloat()))))
                        }
                        viewModel.strokeWidth?.observe(this@MainFragment, Observer {
                            if(it != null) cartesianSystem?.updateGraph(points, it)
                            else errorTextView?.visibility = View.VISIBLE
                        })

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
            val strokeWidth = getFloat(Constants.STROKE_WIDTH, 5F)
            Log.d("start strokeWidth", strokeWidth.toString())
            viewModel.strokeWidth?.postValue(strokeWidth)

            val graphColor = getString(Constants.GRAPH_COLOR, "green")
            Log.d("start graphColor", graphColor.toString())
            viewModel.graphColor?.postValue(graphColor)
        }
    }

    override fun onStop() {
        super.onStop()
        val prefs = activity?.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
        prefs?.run {
            edit().run {
                putFloat(Constants.STROKE_WIDTH, viewModel.strokeWidth?.value?:5F)
                Log.d("stop strokeWidth", viewModel.strokeWidth?.value.toString())
                putString(Constants.GRAPH_COLOR, viewModel.graphColor?.value?:"green")
                Log.d("stop graphColor", viewModel.graphColor?.value.toString())
                apply()
            }
        }
    }

    fun func(x: Float): Float {
        return (1 / x)
    }
}
