package com.example.smkccovid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smkccovid.*
import com.example.smkccovid.activity.MainActivity
import com.example.smkccovid.data.AppConstants
import com.example.smkccovid.data.NewCountryItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.choose_item.*
import util.goTo

class NewCountryAdapter(private val context: Context, private val items : List<NewCountryItem>, private val listener: (NewCountryItem)-> Unit) :
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
        holder.bindItem(items[position], listener)
    }
    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: NewCountryItem, listener: (NewCountryItem) -> Unit) {
            val url = AppConstants.FLAGS_URL+item.iSO2+AppConstants.FLAGS_URL_END

            tv_choose_name.text = item.country.capitalize()
            Glide.with(context).load(url).into(iv_choose)

            bt_select.setOnClickListener {
                val test by lazy { context.getSharedPreferences("test", Context.MODE_PRIVATE) }
                val edit = test.edit()
                edit.putBoolean("update", true)
                edit.putString("slug", item.slug.toLowerCase())
                edit.apply()

                goTo(context, MainActivity(), true)
            }

            containerView.setOnClickListener { listener(item) }
        }
    }

}