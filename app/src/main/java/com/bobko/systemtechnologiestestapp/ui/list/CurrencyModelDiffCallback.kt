package com.bobko.systemtechnologiestestapp.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.bobko.systemtechnologiestestapp.model.Currency

class CurrencyModelDiffCallback : DiffUtil.ItemCallback<Currency?>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.priceUsd == newItem.priceUsd
    }
}