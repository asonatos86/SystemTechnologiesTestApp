package com.bobko.systemtechnologiestestapp.ui.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bobko.systemtechnologiestestapp.R
import com.bobko.systemtechnologiestestapp.databinding.FragmentListBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ListFragment : Fragment() {

    lateinit var currencyViewModel: CurrencyViewModel
    var fragmentView:View?=null
    private var listAdapter:CurrencyListAdapter?=null
    private var currencyListLayoutBinding:FragmentListBinding?=null
    private val PAGE_SIZE = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currencyListLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false)
        fragmentView = currencyListLayoutBinding?.root
        currencyViewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it)
                currencyListLayoutBinding?.root?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
            else
                currencyListLayoutBinding?.root?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.INVISIBLE
        })

        currencyViewModel.showDetails.observe(viewLifecycleOwner, Observer{
            if (it){
                showDetails()
                currencyViewModel.showDetails.value = false
            }

        })

        loadAdapter()
        return fragmentView
    }

    fun showDetails()
    {
        var bundle = Bundle()
        bundle.putString("id", currencyViewModel.targetCurrencyModel?.id)
        activity?.let { it1 -> Navigation.findNavController(it1,R.id.nav_host_fragment).navigate(R.id.infoActivity,bundle) }
    }

    fun  initViewModel(){
        currencyViewModel = activity?.let { ViewModelProvider(it).get(CurrencyViewModel::class.java) }!!
    }

    fun loadAdapter()
    {
        val dataSource = CurrencyModelDataSource(currencyViewModel.getCurrencyApi(), currencyViewModel.showProgress)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        val pagedList = PagedList.Builder(dataSource, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor(MainThreadExecutor())
            .build()

        listAdapter = CurrencyListAdapter(CurrencyModelDiffCallback(), currencyViewModel)
        listAdapter!!.submitList(pagedList)

        currencyListLayoutBinding?.currencyList?.layoutManager = LinearLayoutManager(context)
        currencyListLayoutBinding?.currencyList?.adapter = listAdapter
    }

    internal inner class MainThreadExecutor : Executor {
        private val mHandler: Handler? = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            command?.let { mHandler?.post(it) }
        }
    }
}