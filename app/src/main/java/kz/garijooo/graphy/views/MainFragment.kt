package kz.garijooo.graphy.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kz.garijooo.graphy.R
import kz.garijooo.graphy.models.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel
    private var visibilityBtn: Button? = null
    private var cartesianSystem: CartesianView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        cartesianSystem = activity?.findViewById<CartesianView>(R.id.cartesian)?.apply {
        }
        visibilityBtn = activity?.findViewById<Button>(R.id.visibilityBtn)
        visibilityBtn?.setOnClickListener {
            if(visibilityBtn?.text.toString() == getString(R.string.btn_hide)) {
                visibilityBtn?.text = getString(R.string.btn_show)
                cartesianSystem?.visibility = View.INVISIBLE

            }
            else {
                visibilityBtn?.text = getString(R.string.btn_hide)
                cartesianSystem?.visibility = View.VISIBLE
            }

        }
    }

}