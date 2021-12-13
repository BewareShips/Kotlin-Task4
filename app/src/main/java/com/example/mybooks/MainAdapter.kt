package com.example.mybooks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybooks.databinding.AdapterMovieBinding


public class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var reserveList = mutableListOf<BookItem>()
    var items = mutableListOf<BookItem>()


    fun setReserveItemList(items: List<BookItem>) {
        this.reserveList = items.toMutableList()

    }



    fun setItemList(items: List<BookItem>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text = "Author: ${item.author}"
        holder.binding.title.text = "Title: ${item.title}"
        holder.binding.country.text = "Country: ${item.country}"
        holder.binding.language.text = "Language: ${item.language}"
        holder.binding.pages.text = "Pages: ${item.pages.toString()}"
        holder.binding.year.text = "Year: ${item.year.toString()}"
        holder.binding.link.text = "Link: ${item.link}"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}