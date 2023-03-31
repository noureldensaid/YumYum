package com.fyp.yumyum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.MealsRvItemsBinding
import com.fyp.yumyum.models.Meal
import com.fyp.yumyum.ui.main.MainViewModel
import javax.inject.Inject

class FavAdapter (private val viewModel: MainViewModel) :
    RecyclerView.Adapter<FavAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: MealsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meal) {
            itemView.setOnClickListener { onItemClickListener?.invoke(item) }
            binding.apply {
                categoryName.text = item.strMeal
                icFav.isChecked = true
                Glide.with(itemView)
                    .load(item.strMealThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_wifi_broken)
                    .transform(CenterCrop())
                    .placeholder(R.drawable.logoo)
                    .into(categoryImg)
            }
            binding.icFav.setOnClickListener {
                if (!binding.icFav.isChecked) {
                    viewModel.removeFav(item)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MealsRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((Meal) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}