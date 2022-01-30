package com.example.moviesdemo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tentwentyassignment.models.SearchItemsModel
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdemo.R
import com.example.moviesdemo.activities.DetailPageActivity
import com.example.moviesdemo.adapters.SearchItemListingAdapter
import com.example.moviesdemo.adapters.SearchResultListingAdapter
import com.example.moviesdemo.databinding.FragmentSearchBinding
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.moviesdemo.models.SearchedResult
import com.example.moviesdemo.models.SearchedResultModel
import com.example.moviesdemo.utils.Extensions.hideKeyboard
import com.example.moviesdemo.viewmodels.SearchedFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mViewModel: SearchedFragmentViewModel
    private var mSearchItemResultList = ArrayList<SearchedResult>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setBindings()
        populateDataInModel()
        setObserver()
        callBacks()
        return binding.root
    }

    private fun callBacks() {
        binding.imageClearSearch.setOnClickListener {
            if (binding.editTextSearch.text.toString().isNotEmpty()) {
                binding.editTextSearch.setText("")
            } else {
                setCurrentFragment(WatchFragment())
            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {

                if (s.toString().isNullOrEmpty()) {
                    binding.recyclerBeforeSearch.visibility = View.VISIBLE
                    binding.linearSearchedResults.visibility = View.GONE
                } else {
                    callSearchApi(s.toString())
                }

            }
        })

        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (binding.showEmptyText.visibility == View.VISIBLE) {
                    hideKeyboard()
                } else {
                    hideKeyboard()
                    binding.linearAfterResultFound.visibility = View.VISIBLE
                    binding.linearBeforeResultFound.visibility = View.GONE
                    binding.txtShowTopResult.visibility = View.GONE
                    binding.viewShowTopResult.visibility = View.GONE
                }
                return@setOnEditorActionListener true
            }
            false
        }

        binding.imageBackFromSearch.setOnClickListener {
            binding.linearAfterResultFound.visibility = View.GONE
            binding.linearBeforeResultFound.visibility = View.VISIBLE
            binding.txtShowTopResult.visibility = View.VISIBLE
            binding.viewShowTopResult.visibility = View.VISIBLE
        }
    }

    private fun callSearchApi(query: String) {
        binding.progressSearch.visibility = View.VISIBLE
        mViewModel.callSearchedMovies(query)

    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, fragment)
            commit()
        }

    private fun setObserver() {
        mViewModel.mSearchedMoviesResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                mSearchItemResultList = it.results as ArrayList<SearchedResult>
                binding.searchResultCount.text = it.totalResults.toString() + " Results Found"
                setSearchAdapter()
                binding.progressSearch.visibility = View.VISIBLE
            }
        })
    }

    private fun setSearchAdapter() {
        if (mSearchItemResultList.size > 0) {
            binding.showEmptyText.visibility = View.GONE
            binding.linearSearchedResults.visibility = View.VISIBLE
            binding.recyclerBeforeSearch.visibility = View.GONE
            binding.recyclerAfterSearch.layoutManager = LinearLayoutManager(requireActivity())
            val adapterSecond = SearchResultListingAdapter(requireContext(), mSearchItemResultList, this)
            binding.recyclerAfterSearch.adapter = adapterSecond
        } else {
            binding.showEmptyText.visibility = View.VISIBLE
        }
    }

    private fun setBindings() {
        mViewModel = ViewModelProvider(this).get(SearchedFragmentViewModel::class.java)
    }

    private fun populateDataInModel() {
        val mSearchItemList = ArrayList<SearchItemsModel>()
        mSearchItemList.add(SearchItemsModel("Friends", R.drawable.search_one, "Comedies"))
        mSearchItemList.add(SearchItemsModel("A Time To Kill", R.drawable.search_two, "Crime"))
        mSearchItemList.add(SearchItemsModel("Family", R.drawable.search_three, "Family"))
        mSearchItemList.add(
            SearchItemsModel(
                "Social Dilemma",
                R.drawable.search_four,
                "Documentary"
            )
        )
        mSearchItemList.add(SearchItemsModel("The King", R.drawable.search_five, "Dramas"))
        mSearchItemList.add(SearchItemsModel("Timeless", R.drawable.search_six, "Fantasy"))
        mSearchItemList.add(SearchItemsModel("Holidays", R.drawable.search_seven, "Holidays"))
        mSearchItemList.add(SearchItemsModel("The Mummy", R.drawable.search_eight, "Horror"))
        mSearchItemList.add(SearchItemsModel("In Time", R.drawable.search_nine, "Sci-Fi"))
        mSearchItemList.add(SearchItemsModel("It", R.drawable.search_ten, "Thriller"))

        val mSearchItemResultList = ArrayList<SearchItemsModel>()
        mSearchItemResultList.add(
            SearchItemsModel(
                "Timeless",
                R.drawable.search_result_one,
                "Fantasy"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "In Time",
                R.drawable.search_result_two,
                "Sci-Fi"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "A Time To Kill",
                R.drawable.search_result_three,
                "Crime"
            )
        )
        binding.recyclerBeforeSearch.visibility = View.VISIBLE
        binding.linearSearchedResults.visibility = View.GONE
        binding.recyclerBeforeSearch.layoutManager = GridLayoutManager(requireActivity(), 2)
        val adapter = SearchItemListingAdapter(mSearchItemList)
        binding.recyclerBeforeSearch.adapter = adapter

    }

    override fun watchItemCLicked(movieId: Int) {
        val intent = Intent(requireContext(), DetailPageActivity::class.java)
        intent.putExtra("mIntentID", movieId)
        startActivity(intent)
    }


}