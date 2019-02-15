package com.alexey.bags_mvc_example.ui.main.adapters

import android.arch.lifecycle.MutableLiveData
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


class ProductCatalogAdapter(private val searchResultItems: MutableLiveData<ArrayList<ResultsItem?>?>): RecyclerView.Adapter<ProductCatalogAdapter.ViewHolder>() {


    private var onClickListener: OnItemClicked? = null

    interface OnItemClicked {
        fun onProductListItemClick(position: Int, productType: ProductType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_catalog_item, parent, false))

    override fun getItemCount(): Int {
        return if(searchResultItems.value?.size != null)
            searchResultItems.value?.size!!
        else
            0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val images = searchResultItems.value?.get(position)?.image
        val title = searchResultItems.value?.get(position)?.title

        Picasso.get().load(images).into(holder.imageView)
        holder.textView.text = title

        holder.itemLayout.setOnClickListener {
            onClickListener?.onProductListItemClick(position, ProductType.PRODUCT_LIST_ITEM)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.image)
        var textView: TextView = itemView.findViewById(R.id.title)
        var itemLayout: CardView = itemView.findViewById(R.id.itemLayout)
    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClickListener = onClick
    }
}