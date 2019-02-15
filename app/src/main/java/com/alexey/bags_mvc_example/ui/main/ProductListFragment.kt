package com.alexey.bags_mvc_example.ui.main

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexey.bags_mvc_example.R
import com.alexey.bags_mvc_example.enums.ProductType
import com.alexey.bags_mvc_example.models.Response
import com.alexey.bags_mvc_example.models.ResultsItem
import com.alexey.bags_mvc_example.ui.main.adapters.ProductCatalogAdapter
import com.alexey.bags_mvc_example.ui.main.adapters.ProductWishListAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.product_details_fragment.*
import kotlinx.android.synthetic.main.start_activity.*
import kotlinx.android.synthetic.main.product_list_fragment.*
import android.content.DialogInterface
import android.R.string.cancel
import android.support.v7.app.AlertDialog


class ProductListFragment : Fragment(), ProductCatalogAdapter.OnItemClicked, ProductWishListAdapter.OnItemClicked {

    companion object {
        val TAG = ProductListFragment::class.toString()
        fun newInstance() = ProductListFragment()
    }

    var mCallbackProductSelected: OnProductSelectedListener? = null

    interface OnProductSelectedListener {
        fun onProductSelected(
            item: ResultsItem?,
            productType: ProductType
        )
    }

    private val items = MutableLiveData<ArrayList<ResultsItem?>?>()
    var wishListItems = MutableLiveData<ArrayList<ResultsItem?>?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(false)
        toolbar?.title = ""

        val toolbarTitle: TextView = (activity as AppCompatActivity).toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle.text = getString(R.string.app_name)
        toolbarTitle.textSize = (20).toFloat()
        toolbarTitle.setTypeface(null, Typeface.BOLD)
        val params = toolbarTitle.layoutParams as Toolbar.LayoutParams
        params.gravity = Gravity.START

        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        productCatalogRecyclerView.layoutManager = horizontalLayoutManager
        val adapter = ProductCatalogAdapter(items)
        adapter.setOnClick(this)
        productCatalogRecyclerView.adapter = adapter

        val verticalLayoutManagerWishList = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        wishListRecyclerView.layoutManager = verticalLayoutManagerWishList
        val adapterWishList = ProductWishListAdapter(wishListItems)
        adapterWishList.setOnClick(this)
        wishListRecyclerView.adapter = adapterWishList

        var totalPrice = 0f
        wishListItems.value?.let {
            for(item in it){
                totalPrice = totalPrice.plus(item?.price?.toFloat()!!)
            }
        }

        wish_list_total.text = String.format(resources.getString(R.string.product_wish_list_total), totalPrice)
        wish_list_subtotal_price.text = String.format(resources.getString(R.string.product_wish_list_subtotal_price), totalPrice)

        val fileContent = resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }
        val resultJson: Response = Gson().fromJson(fileContent, object : TypeToken<Response>() {}.type)


        checkout_button.setOnClickListener {
            val builder = AlertDialog.Builder(activity as AppCompatActivity)
            builder.setMessage(R.string.product_wish_list_checkout_message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
            builder.create()
            builder.show()
        }

        items.value = resultJson.results as ArrayList<ResultsItem?>?

    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        try {
            mCallbackProductSelected = activity as OnProductSelectedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnProductSelectedListener")
        }
    }

    override fun onProductListItemClick(position: Int, productType: ProductType) {
        if(productType.equals(ProductType.PRODUCT_LIST_ITEM)) {
            mCallbackProductSelected?.onProductSelected(items.value?.get(position), productType)
        } else {
            mCallbackProductSelected?.onProductSelected(wishListItems.value?.get(position), productType)
        }

    }

    fun updateWishList(itemsWishList: MutableLiveData<ArrayList<ResultsItem?>?>) {

        val list = arrayListOf<ResultsItem?>()
        list.addAll(itemsWishList.value!!)

        wishListItems.value = list

    }


}
