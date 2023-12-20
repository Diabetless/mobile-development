package com.ch2Ps073.diabetless.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.databinding.ItemArticleCarouselBinding
import com.ch2Ps073.diabetless.ui.detail.DetailActivity

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.ListViewHolder>() {

    private val articleList: MutableList<ArticleItem> = mutableListOf()

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
        val item = articleList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(KEY_ID, item.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = articleList.size

    fun setList(list: List<ArticleItem>) {
        articleList.clear()
        articleList.addAll(list)
    }

    companion object {
        const val KEY_ID = "key_id"
    }
}