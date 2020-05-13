package com.example.smkccovid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smkccovid.Country
import com.example.smkccovid.NewCountryItem
import com.example.smkccovid.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.choose_item.*
import kotlinx.android.synthetic.main.countries_item.*

class NewCountryAdapter(private val context: Context, private val items : List<NewCountryItem>) :
    RecyclerView.Adapter<NewCountryAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder( context,
            LayoutInflater.from(context).inflate(
                R.layout.choose_item,
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
    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: NewCountryItem) {

            val url = "https://www.countryflags.io/"+item.iSO2+"/flat/64.png"

            tv_choose_name.text = item.country.capitalize()
            Glide.with(context).load(url).into(iv_choose)
        }
    }
}