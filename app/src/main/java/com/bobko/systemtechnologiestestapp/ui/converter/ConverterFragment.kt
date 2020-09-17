package com.bobko.systemtechnologiestestapp.ui.converter

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bobko.systemtechnologiestestapp.R
import com.bobko.systemtechnologiestestapp.databinding.FragmentConverterBinding
import com.bobko.systemtechnologiestestapp.model.Currency
import com.bobko.systemtechnologiestestapp.ui.info.DecimalDigitsInputFilter
import com.bobko.systemtechnologiestestapp.ui.info.InfoViewModel


class ConverterFragment : Fragment() {

    lateinit var currencyViewModel: ConvertViewModel
    var fragmentView:View?=null

    private var fragmentConverterBinding: FragmentConverterBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentConverterBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_converter,container,false)
        initViewModel()

        fragmentConverterBinding?.viewModel = currencyViewModel

        fragmentView = fragmentConverterBinding?.root
        fragmentConverterBinding?.editTextNumberDecimal?.filters =
            arrayOf<InputFilter>(DecimalDigitsInputFilter(10, 2))

        initListeners()
        return fragmentView
    }

    fun  initViewModel(){
        currencyViewModel = activity?.let { ViewModelProvider(it).get(ConvertViewModel::class.java) }!!
        currencyViewModel.currencyList.observe(viewLifecycleOwner, Observer {
            currencyViewModel.loadDefault()
            fragmentConverterBinding?.invalidateAll()
        })
    }

    fun initListeners()
    {
        currencyViewModel.selectCurrencyFromTrigger.observe(viewLifecycleOwner, Observer { it ->
            if (it){
                currencyViewModel.selectCurrencyFromTrigger.value = false
                loadDialog().observe(viewLifecycleOwner, Observer {
                    currencyViewModel.currencyFrom.set(it)
                    currencyViewModel.loadPrice()
                })
            }
        })
        currencyViewModel.selectCurrencyToTrigger.observe(viewLifecycleOwner, Observer { it ->
            if (it){
                currencyViewModel.selectCurrencyToTrigger.value = false
                loadDialog().observe(viewLifecycleOwner, Observer {
                    currencyViewModel.currencyTo.set(it)
                    currencyViewModel.loadPrice()
                })
            }
        })
    }

    fun loadDialog(): MutableLiveData<Currency>{
        var result = MutableLiveData<Currency>()
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Choose currency")

        val names =
            currencyViewModel.getNameList().toTypedArray()
        builder.setItems(names, DialogInterface.OnClickListener { dialog, which ->
            result.value = currencyViewModel.currencyList.value?.get(which)!!
        })

        val dialog: android.app.AlertDialog? = builder.create()
        dialog?.show()

        return result
    }
}