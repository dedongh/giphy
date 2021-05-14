package com.engineerskasa.giphy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.engineerskasa.giphy.R
import com.engineerskasa.giphy.databinding.ItemGiphyBinding
import com.engineerskasa.giphy.model.Data

class TrendingAdapter (
    val data: ArrayList<Data>
        ): RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    private lateinit var itemGiphyBinding: ItemGiphyBinding
    class TrendingViewHolder(val itemGiphyBinding: ItemGiphyBinding) :
        RecyclerView.ViewHolder(itemGiphyBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        itemGiphyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_giphy, parent, false)
        return TrendingViewHolder(itemGiphyBinding)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.itemGiphyBinding.data = data[position]
    }

    override fun getItemCount(): Int = data.size

    fun setUpData(giphies: List<Data>) {
        data.clear()
        data.addAll(giphies)
        notifyDataSetChanged()
    }
}