package com.example.baseapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.databinding.ListItemHeaderBinding

class PagedNewsAdapter: PagingDataAdapter<HeaderField, PagedNewsAdapter.HeaderViewHolder>(
    HeaderDiffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HeaderViewHolder = HeaderViewHolder(
        ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class HeaderViewHolder(
        private val binding: ListItemHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(input: HeaderField) {
            binding.txtTitle.text = input.webTitle
            binding.imageNews.load(input.thumbnail)
            binding.txtDate.text = input.webPublicationDate
        }
    }
}

object HeaderDiffCallback : DiffUtil.ItemCallback<HeaderField>() {
    override fun areItemsTheSame(
        oldItem: HeaderField,
        newItem: HeaderField,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: HeaderField,
        newItem: HeaderField,
    ): Boolean = oldItem == newItem
}
