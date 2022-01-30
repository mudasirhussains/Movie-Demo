package com.example.moviesdemo.activities

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.bumptech.glide.request.RequestOptions
import com.example.moviesdemo.R
import com.example.moviesdemo.adapters.GenresListingAdapter
import com.example.moviesdemo.databinding.ActivityDetailPageBinding
import com.example.moviesdemo.models.Genre
import com.example.moviesdemo.utils.Constants
import com.example.moviesdemo.viewmodels.DetailPageViewModel
import java.util.*


@AndroidEntryPoint
class DetailPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailPageBinding
    lateinit var mViewModel: DetailPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setBindings()
        callMoviesApi()
        setObserver()
        callBacks()
    }

    private fun setObserver() {
        mViewModel.mDetailPageResponse.observe(this, Observer {
            if (it != null) {
                setGenreAdapter(it.genres)
                Glide.with(this)
                    .load(Constants.IMAGE_URL + it.backdropPath)
//                    .apply(RequestOptions().override(600, 200))
                    .into(binding.posterImage!!)


                var year = Calendar.getInstance().get(Calendar.YEAR).toString()
                if (it.releaseDate.contains(year)){
                    binding.txtReleaseDate.setTypeface(null, Typeface.BOLD)
                    binding.txtReleaseDate.setTextColor(ContextCompat.getColor(applicationContext, R.color.red));
                }

                binding.txtReleaseDate.text = "In Theaters " + it.releaseDate
                binding.txtDescription.text = it.overview
                binding.txtMovieTitle.text = it.title
                binding.txtVoteAverage.text = "Votes " +it.voteAverage.toString()
                binding.txtCount.text = "Counts " +it.voteCount.toString()
                binding.txtRevenue.text = "Revenue: " +it.revenue
                binding.txtDuration.text = "Duration: " +it.runtime+" mins"
                binding.progressBarDetailPage?.visibility = View.GONE
            }
        })
    }

    private fun setGenreAdapter(genres: List<Genre>) {
        binding.recyclerGenres.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = GenresListingAdapter(this, genres)
        binding.recyclerGenres.adapter = adapter
    }


    private fun callMoviesApi() {
        binding.progressBarDetailPage?.visibility = View.VISIBLE
        val movieId = intent.getIntExtra("mIntentID", 0)
        mViewModel.callDetailPageData(movieId)

    }

    private fun callBacks() {
        binding.imageBackFromDetail.setOnClickListener {
            finish()
        }
    }

    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun setBindings() {
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this).get(DetailPageViewModel::class.java)
    }
}