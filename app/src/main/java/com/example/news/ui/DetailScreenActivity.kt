package com.example.news.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.ActivityDetailScreenBinding
import com.example.news.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val article = intent.getSerializableExtra("key") as? Article
        if (extras != null) {
            Log.d("MANSHI", "onCreate: $article")
        }
        binding.headline.text = article?.title

        val rawAuthor = article?.author

// Use a regular expression to extract the text between ">" and "<"
        val regex = Regex(">.*<")
        val matchResult = rawAuthor?.let { regex.find(it) }
        val extractedAuthor = matchResult?.value?.substring(1, matchResult.value.length - 1)


        binding.authorName.text = extractedAuthor ?: "Anonymous"

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateTime = LocalDateTime.parse(article?.publishedAt, formatter)

        // Format the LocalDateTime object to get the date portion
        val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        binding.tvPublishedAt.text = formattedDate
        Glide.with(this)
            .load(article?.urlToImage)
            .placeholder(R.drawable.placeholder) // Optional placeholder while loading
            .error(R.drawable.error) // Optional error image if the load fails
            .into(binding.newsImage)
        binding.description.text = article?.description
        binding.content.text = article?.content
        val url=article?.url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        binding.openInBrowser.setOnClickListener {

            startActivity(intent)

        }
    }
}