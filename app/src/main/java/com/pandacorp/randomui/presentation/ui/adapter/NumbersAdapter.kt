package com.pandacorp.randomui.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.randomui.databinding.NumberItemBinding
import com.pandacorp.randomui.domain.model.NumberItem

class NumbersAdapter : ListAdapter<NumberItem, NumbersAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<NumberItem>() {
        override fun areItemsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NumberItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(private val binding: NumberItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(numberItem: NumberItem) {
            binding.idTextView.text = buildString {
                append(numberItem.id + 1)
                append(")")
            }
            binding.mainTextView.text = numberItem.number.toString()
        }
    }
}