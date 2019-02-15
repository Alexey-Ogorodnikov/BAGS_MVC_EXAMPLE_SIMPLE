package com.alexey.bags_mvc_example.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexey.bags_mvc_example.R
import com.alexey.bags_mvc_example.enums.ProductType
import com.alexey.bags_mvc_example.models.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_details_fragment.*
import kotlinx.android.synthetic.main.start_activity.*


class ProductDetailsFragment : Fragment() {

    companion object {
        private const val ITEM_KEY = "item_key"
        private const val PRODUCT_TYPE_KEY = "product_type_key"

        fun newInstance(
            item: ResultsItem?,
            productType: ProductType
        ): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ITEM_KEY, item)
            bundle.putSerializable(PRODUCT_TYPE_KEY, productType)
            fragment.arguments = bundle

            return fragment
        }
    }

    var mCallback: OnAddToWishListListener? = null

    interface OnAddToWishListListener {
        fun onActionProductToWishList(
            item: ResultsItem,
            resultsItemType: ProductType
        )    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        try {
            mCallback = activity as ProductDetailsFragment.OnAddToWishListListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnAddToWishListListener")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val resultsItem = arguments!!.getSerializable(ITEM_KEY) as ResultsItem
        val resultsItemType = arguments!!.getSerializable(PRODUCT_TYPE_KEY) as ProductType

        val toolbarView = (activity as AppCompatActivity).supportActionBar
        toolbarView?.setDisplayHomeAsUpEnabled(true)
        toolbarView?.setHomeAsUpIndicator(R.drawable.baseline_keyboard_arrow_left_white_18dp)
        val toolbarTitle: TextView = (activity as AppCompatActivity).toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle.text = resultsItem.title
        toolbarTitle.textSize = (16).toFloat()
        toolbarTitle.setTypeface(null, Typeface.NORMAL)
        val params = toolbarTitle.layoutParams as Toolbar.LayoutParams
        params.gravity = Gravity.CENTER


        Picasso.get().load(resultsItem.image).into(product_image)
        description.text = resultsItem.description
        price.text = resultsItem.currencyCode+" "+resultsItem.price

        product_details_size_H.text = resultsItem.sizeH
        product_details_size_W.text = resultsItem.sizeW
        product_details_size_D.text = resultsItem.sizeD

        product_details_colours_1.background.colorFilter = PorterDuffColorFilter(Color.parseColor(resultsItem.colours1), PorterDuff.Mode.SRC_ATOP)
        product_details_colours_2.background.colorFilter = PorterDuffColorFilter(Color.parseColor(resultsItem.colours2), PorterDuff.Mode.SRC_ATOP)

        if(resultsItemType.equals(ProductType.PRODUCT_LIST_ITEM)) {
            action_wishlist_button.text = resources.getString(R.string.product_wish_list_add)
            action_wishlist_button.background.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context!!, R.color.red), PorterDuff.Mode.SRC_ATOP)
        } else {
            action_wishlist_button.text = resources.getString(R.string.product_wish_list_remove)
            action_wishlist_button.background.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context!!, R.color.black), PorterDuff.Mode.SRC_ATOP)
        }

        action_wishlist_button.setOnClickListener {
            mCallback?.onActionProductToWishList(resultsItem, resultsItemType)
            (activity as AppCompatActivity).onBackPressed()
        }


    }
}