package com.example.moviesdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tentwentyassignment.R
import com.example.tentwentyassignment.models.SearchItemsModel

class SearchResultListingAdapter(private var mList: ArrayList<SearchItemsModel>) :
    RecyclerView.Adapter<SearchResultListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]
        holder.imageSearchResult.setImageResource(vList.itemImage)
        holder.titleSearchResult.text = vList.itemTitle
        holder.subTitleSearchResult.text = vList.itemCategory
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSearchResult = itemView.findViewById(R.id.imageSearchResult) as ImageView
        val titleSearchResult = itemView.findViewById(R.id.titleSearchResult) as TextView
        val subTitleSearchResult = itemView.findViewById(R.id.subTitleSearchResult) as TextView
    }

    fun filterSearch(filteredList: ArrayList<SearchItemsModel>) {
        mList = filteredList
        notifyDataSetChanged()
    }
}