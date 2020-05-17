package com.example.smkccovid.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smkccovid.*
import com.example.smkccovid.data.AppConstants
import com.example.smkccovid.data.CountrySummary
import id.voela.actrans.AcTrans
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.choose_item.*
import kotlinx.android.synthetic.main.countries_item.*
import kotlinx.android.synthetic.main.rank_item.*
import util.goTo
import util.tampilToast
import java.util.*

class RankAdapter(private val context: Context, private val items : List<CountrySummary>, private val type: Int) :
    RecyclerView.Adapter<RankAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder( context,
            LayoutInflater.from(context).inflate(
                R.layout.rank_item,
                parent,
                false
            )
        )
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], position+1, type)
    }
    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: CountrySummary, position: Int, type: Int) {
            val url = AppConstants.FLAGS_URL+item.countryCode+AppConstants.FLAGS_URL_END
            val dec = java.text.DecimalFormat("#,###")

            when (type) {
                0 -> rl_rank.setBackgroundResource(R.drawable.gradient_orange)
                1 -> rl_rank.setBackgroundResource(R.drawable.gradient_green)
                2 -> rl_rank.setBackgroundResource(R.drawable.gradient_red)
            }

            when (type) {
                0 -> tv_rank_amount.text = dec.format(item.totalConfirmed) + " " + context.getString(R.string.cases)
                1 -> tv_rank_amount.text = dec.format(item.totalRecovered) + " " + context.getString(R.string.recovered)
                2 -> tv_rank_amount.text = dec.format(item.totalDeaths) + " " + context.getString(R.string.deaths)
            }

            tv_rank_rank.text = position.toString()
            tv_rank_name.text = item.country.capitalize()
            Glide.with(context).load(url).into(iv_rank)
        }
    }

}