package com.bobko.systemtechnologiestestapp.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bobko.systemtechnologiestestapp.R
import com.bobko.systemtechnologiestestapp.databinding.CurrencyListItemBinding
import com.bobko.systemtechnologiestestapp.model.Currency

class CurrencyListAdapter (diffUtilCallback: CurrencyModelDiffCallback,var viewModel: CurrencyViewModel) : PagedListAdapter<Currency, CurrencyListAdapter.ViewHolder>(diffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CurrencyListItemBinding = DataBindingUtil.inflate(inflater, R.layout.currency_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(binding: CurrencyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: CurrencyListItemBinding? = binding
        fun bind(currency: Currency?) {
            binding?.root?.setOnClickListener(View.OnClickListener {
                viewModel.targetCurrencyModel = currency
                viewModel.showDetails.value = true
            })
            binding?.currency = currency
            binding?.executePendingBindings()
        }

    }
}