package com.andy_dev.arpractical.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andy_dev.arpractical.R
import com.andy_dev.arpractical.databinding.RowItemBinding
import com.andy_dev.arpractical.model.Facts

class FactsListAdapter(
    private var data: ArrayList<Facts>
) : RecyclerView.Adapter<FactsListAdapter.FactsListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsListVH {
        val v: RowItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_item,
            parent,
            false
        )
        return FactsListVH(v)
    }

    override fun onBindViewHolder(holder: FactsListVH, position: Int) {
        val resultsItem = data[position]
        holder.binding.model = resultsItem
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FactsListVH(val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}