package com.example.smkccovid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smkccovid.Country
import com.example.smkccovid.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.countries_item.*

class CountryAdapter(private val context: Context, private val items : ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.countries_item,
                parent,
                false
            )
        )
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position))
    }
    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Country) {
            tv_name.text = item.name
            tv_confirmed.text = item.confirmed
        }
    }
}