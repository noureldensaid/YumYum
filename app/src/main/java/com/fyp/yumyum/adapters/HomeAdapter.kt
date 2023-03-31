package com.fyp.yumyum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.HomeRvItemsBinding
import com.fyp.yumyum.models.Category

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: HomeRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            binding.apply {
                categoryName.text = item.strCategory
            }
            Glide.with(itemView)
                .load(item.strCategoryThumb)
                .transform(CenterCrop())
                .error(R.drawable.ic_wifi_broken)
                .placeholder(R.drawable.logoo)
                .into(binding.categoryImg)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((Category) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}