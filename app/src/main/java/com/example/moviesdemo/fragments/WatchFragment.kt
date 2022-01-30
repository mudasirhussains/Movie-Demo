package com.example.moviesdemo.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesdemo.R
import com.example.moviesdemo.activities.DetailPageActivity
import com.example.moviesdemo.adapters.WatchItemListingAdapter
import com.example.moviesdemo.databinding.FragmentWatchBinding
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.tentwentyassignment.models.OfflineMovieModel
import com.example.tentwentyassignment.models.UpcomingResult
import com.example.moviesdemo.viewmodels.WatchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentWatchBinding
    private lateinit var mViewModel: WatchFragmentViewModel
    private var arrayListUpcomingMovies: ArrayList<UpcomingResult> = ArrayList()
    private var offlineMovies: List<OfflineMovieModel> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchBinding.inflate(inflater, container, false)
        setBindings()
        clickListeners()
        if (isNetworkAvailable(requireContext())){
            callMoviesApi()
            setObserver()
        }
//        else{
//            setOfflineAdapter()
//        }
        return binding.root
    }

    private fun setBindings() {
        mViewModel = ViewModelProvider(this).get(WatchFragmentViewModel::class.java)
        //dao = AppDatabase.getInstance(requireContext()).dao()!!
    }

    private fun setOfflineAdapter() {
        //offlineMovies =  dao.getAll()
    }

    private fun setObserver() {
        mViewModel.mUpcomingMoviesResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                arrayListUpcomingMovies.addAll(it.results)
                //dao.insertData(offlineMovies)
                setUpcomingMoviesAdapter(arrayListUpcomingMovies)
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun setUpcomingMoviesAdapter(arrayListUpcomingMovies: ArrayList<UpcomingResult>) {
        binding.recyclerWatchFragment.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = WatchItemListingAdapter(requireContext(), arrayListUpcomingMovies, this)
        binding.recyclerWatchFragment.adapter = adapter
    }


    private fun callMoviesApi() {
        binding.progressBar.visibility = View.VISIBLE
        mViewModel.callUpcomingMovies()
    }

    private fun clickListeners() {
        binding.imageSearchView.setOnClickListener {
            setCurrentFragment(SearchFragment())
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, fragment)
            commit()
        }

    override fun watchItemCLicked(movieId : Int) {
        val intent = Intent(requireContext(), DetailPageActivity::class.java)
        intent.putExtra("mIntentID", movieId)
        startActivity(intent)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }


}