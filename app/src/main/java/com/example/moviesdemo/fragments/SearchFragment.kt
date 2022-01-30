package com.example.moviesdemo.fragments

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
import com.example.tentwentyassignment.utils.Extensions.hideKeyboard
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        populateDataInModel("")
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
                populateDataInModel(s.toString())
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

    private fun populateDataInModel(keyword: String) {
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

        if (keyword.isEmpty()) {
            binding.recyclerBeforeSearch.visibility = View.VISIBLE
            binding.linearSearchedResults.visibility = View.GONE
            binding.recyclerBeforeSearch.layoutManager = GridLayoutManager(requireActivity(), 2)
            val adapter = SearchItemListingAdapter(mSearchItemList)
            binding.recyclerBeforeSearch.adapter = adapter
        } else {
            binding.linearSearchedResults.visibility = View.VISIBLE
            binding.recyclerBeforeSearch.visibility = View.GONE
            binding.recyclerAfterSearch.layoutManager = LinearLayoutManager(requireActivity())
            val adapterSecond = SearchResultListingAdapter(mSearchItemResultList)
            binding.recyclerAfterSearch.adapter = adapterSecond

            val filteredList: ArrayList<SearchItemsModel> = ArrayList()
            for (item in mSearchItemResultList) {
                if (item.itemTitle.isNullOrBlank()) {
                    binding.showEmptyText.visibility = View.VISIBLE
                } else {
                    if (item.itemTitle.lowercase(Locale.getDefault()).contains(keyword.lowercase(
                            Locale.getDefault()
                        ))) {
                        filteredList.add(item)
                    }
                }
            }
            adapterSecond.filterSearch(filteredList)

            binding.searchResultCount.text = filteredList.size.toString() + " Results Found"
            if (filteredList.size == 0) {
                binding.showEmptyText.visibility = View.VISIBLE
            } else {
                binding.showEmptyText.visibility = View.GONE
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, fragment)
            commit()
        }
}