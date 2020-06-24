package com.example.smkccovid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smkccovid.R
import com.example.smkccovid.activity.DetailActivity
import com.example.smkccovid.activity.WebViewActivity
import com.example.smkccovid.data.NewsItem
import com.example.smkccovid.model.SelectedCountryModel
import id.voela.actrans.AcTrans
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_item.*
import kotlinx.android.synthetic.main.news_item.*
import util.getDate
import util.goTo
import util.goToWithBoolean
import util.prettyCount
import java.util.*
import kotlin.collections.ArrayList

class DashboardCountryAdapter(private val context: Context, private val items : ArrayList<SelectedCountryModel>) :
    RecyclerView.Adapter<DashboardCountryAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.dashboard_item,
                parent,
                false
            )
        )
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, items.get(position))
    }
    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(context: Context, item: SelectedCountryModel) {
            tv_country_item.text = item.country.capitalize()

            num_blue1_item.text = prettyCount(context, item.totalConfirmed)
            num_green1_item.text = prettyCount(context, item.totalRecovered)
            num_blue1_item.text = prettyCount(context, item.totalDeaths)

            num_blue2_item.text = "+" + item.newConfirmed
            num_green2_item.text = "+" + item.newRecovered
            num_red2_item.text = "+" + item.newDeaths

            ll_blue_item.setOnClickListener {
                goToWithBoolean(context, false, 0)
            }

            ll_green_item.setOnClickListener {
                goToWithBoolean(context, false, 1)
            }

            ll_red_item.setOnClickListener {
                goToWithBoolean(context, false, 2)
            }
        }
    }
}