package com.alexey.bags_mvc_example.ui.main.adapters

import android.arch.lifecycle.MutableLiveData
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alexey.bags_mvc_example.R
import com.alexey.bags_mvc_example.enums.ProductType
import com.alexey.bags_mvc_example.models.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_details_fragment.*


class ProductWishListAdapter(private val searchResultItems: MutableLiveData<ArrayList<ResultsItem?>?>): RecyclerView.Adapter<ProductWishListAdapter.ViewHolder>() {


    private var onClickListener: OnItemClicked? = null


    interface OnItemClicked {
        fun onProductListItemClick(position: Int, productType: ProductType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_wish_list_item, parent, false))

    override fun getItemCount(): Int {
        return if(searchResultItems.value?.size != null)
            searchResultItems.value?.size!!
        else
            0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val resultsItem = searchResultItems.value?.get(position)
        val images = resultsItem?.image
        val title = resultsItem?.title
        val price = resultsItem?.price
        val currencyCode = resultsItem?.currencyCode
        val description = resultsItem?.description

        Picasso.get().load(images).into(holder.imageView)
        holder.title.text = title
        holder.price.text = "$currencyCode $price"
        holder.description.text = description

        holder.product_wish_colours_1.background.colorFilter = PorterDuffColorFilter(Color.parseColor(resultsItem?.colours1), PorterDuff.Mode.SRC_ATOP)
        holder.product_wish_colours_2.background.colorFilter = PorterDuffColorFilter(Color.parseColor(resultsItem?.colours2), PorterDuff.Mode.SRC_ATOP)

        holder.itemLayout.setOnClickListener {
            onClickListener?.onProductListItemClick(position, ProductType.WISH_LIST_ITEM)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.image)
        var price: TextView = itemView.findViewById(R.id.price)
        var title: TextView = itemView.findViewById(R.id.title)
        var description: TextView = itemView.findViewById(R.id.description)
        var itemLayout: CardView = itemView.findViewById(R.id.itemLayout)
        var product_wish_colours_1: CardView = itemView.findViewById(R.id.product_wish_colours_1)
        var product_wish_colours_2: CardView = itemView.findViewById(R.id.product_wish_colours_2)
    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClickListener = onClick
    }
}