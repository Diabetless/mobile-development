package com.CH2PS073.diabetless.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.databinding.ItemArticlesRowBinding

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    private val articleList: ArrayList<ArticleItem> = arrayListOf()
    var onItemClick: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listUser: ArrayList<ArticleItem>) {
        this.articleList.clear()
        this.articleList.addAll(listUser)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<ArticleItem>) {
        this.articleList.clear()
        articleList.addAll(filterlist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticlesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount() = articleList.size

    inner class ViewHolder(private var binding: ItemArticlesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ArticleItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load(user.imageUrl)
                    .into(binding.ivArticle)


                tvTitleArticle.text = user.title
                tvDateArticle.text = user.postDate

                root.setOnClickListener {
                    onItemClick?.invoke(user.id!!)
                }
            }
        }
    }
}