package com.example.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.ItemArticlePreviewBinding
import com.example.news.model.Article


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    //    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivArticleImage = binding.ivArticleImage
        val tvSource = binding.tvSource
        val tvTitle= binding.tvTitle

        val tvDescription=binding.tvDescription

        val tvPublishedAt=binding.tvPublishedAt
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.ivArticleImage)
            holder.tvSource.text = article.source.name
            holder.tvTitle.text = article.title
            holder.tvDescription.text = article.description
            holder.tvPublishedAt.text = article.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }

}