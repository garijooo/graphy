package kz.garijooo.graphy.controllers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import kz.garijooo.graphy.R
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
    // editTexts
    private var editTextStartOX: EditText? = null
    private var editTextEndOX: EditText? = null
    private var editTextStartOY: EditText? = null
    private var editTextEndOY: EditText? = null
    // limitations
    private var startOX: Float? = null
    private var endOX: Float? = null
    private var startOY: Float? = null
    private var endOY: Float? = null
    // sub components
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

        cartesianSystem = activity?.findViewById<CartesianView>(R.id.cartesian)?.apply {

        }
//        converter = ConverterModel(this.startOX!!, this.endOX!!, cartesianSystem!!.width.toFloat())




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

        editTextEndOY = activity?.findViewById<EditText>(R.id.ox_end)?.apply {
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

        errorTextView = activity?.findViewById<TextView>(R.id.error_textView)?.apply {
            this.visibility = View.INVISIBLE
        }
        visibilityBtn = activity?.findViewById<Button>(R.id.visibilityBtn)?.apply {
        }
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

                        cartesianSystem?.startOX = this.startOX!!
                        cartesianSystem?.endOX = this.endOX!!

                        var axisOX: MutableList<Float> = mutableListOf<Float>()
                        var diff: Int = this.endOX!!.toInt() - this.startOX!!.toInt()
                        for(i in 0..diff) {
                            var value: Float = i.toFloat()
                            if(converter?.toDpOX(value) != null) {
                                axisOX.add(converter!!.toDpOX(value))
                            }

                        }



                        var points: MutableList<Float> = mutableListOf<Float>()
                        var size: Int = cartesianSystem?.width ?: 1

                        for(i in 0..(size - 1).toInt()){
                            points.add(converter.toDpOY(this.func(converter.toCartesianOX(i.toFloat()))))
                        }
                        cartesianSystem?.updatePoints(points)
                    }
                }
                else {
                    errorTextView?.visibility = View.VISIBLE
                }
            }

        }
    }

    fun func(x: Float): Float {
        return (x * x - 2)
    }
}