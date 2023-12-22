package com.ch2Ps073.diabetless.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.databinding.ItemArticleCarouselBinding
import com.ch2Ps073.diabetless.ui.detail.DetailActivity

class CarouselAdapter : ListAdapter<ArticleItem, CarouselAdapter.ListViewHolder>(DIFF_CALLBACK) {

    @SuppressLint("SetTextI18n")
    class ListViewHolder(private val binding: ItemArticleCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleItem) {
            binding.articleText.text = "${item.title}\n"
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.imageCarousel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemArticleCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(KEY_ID, item.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object {
        const val KEY_ID = "key_id"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}