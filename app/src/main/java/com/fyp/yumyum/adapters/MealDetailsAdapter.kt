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
import com.fyp.yumyum.databinding.MealDetailsLayoutBinding
import com.fyp.yumyum.models.MealDetails

class MealDetailsAdapter : RecyclerView.Adapter<MealDetailsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: MealDetailsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealDetails) {
            itemView.setOnClickListener { onItemClickListener?.invoke(item) }
            binding.apply {
                mealName.text = item.strMeal
                mealOrigin.text = item.strArea
                mealTags.text = item.strTags
                mealRecipe.text = item.strInstructions
                capacity1.text = item.strMeasure1
                capacity2.text = item.strMeasure2
                capacity3.text = item.strMeasure3
                capacity4.text = item.strMeasure4
                capacity5.text = item.strMeasure5
                ingredient1.text = item.strIngredient1
                ingredient2.text = item.strIngredient2
                ingredient3.text = item.strIngredient3
                ingredient4.text = item.strIngredient4
                ingredient5.text = item.strIngredient5
                Glide.with(itemView)
                    .load(item.strMealThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_wifi_broken)
                    .transform(CenterCrop())
                    .placeholder(R.drawable.placeholder)
                    .into(mealIv)
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MealDetails>() {
        override fun areItemsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MealDetailsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((MealDetails) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}