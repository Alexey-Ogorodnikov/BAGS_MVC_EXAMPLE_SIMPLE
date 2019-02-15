package com.alexey.bags_mvc_example

import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.alexey.bags_mvc_example.enums.ProductType
import com.alexey.bags_mvc_example.models.ResultsItem
import com.alexey.bags_mvc_example.ui.main.ProductDetailsFragment
import com.alexey.bags_mvc_example.ui.main.ProductListFragment


class StartActivity : AppCompatActivity() , ProductListFragment.OnProductSelectedListener, ProductDetailsFragment.OnAddToWishListListener {

    val itemsWishList = MutableLiveData<ArrayList<ResultsItem?>?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProductListFragment.newInstance(), ProductListFragment.TAG)
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return true
    }

    override fun onActionProductToWishList(item: ResultsItem, resultsItemType: ProductType) {
        val fragment = supportFragmentManager.findFragmentByTag(ProductListFragment.TAG)
        //wish list stored in Activity updated from DetailsFragment via callback

        //add and remove actions
        val list = arrayListOf<ResultsItem?>()
        if(resultsItemType.equals(ProductType.PRODUCT_LIST_ITEM)) {
            list.add(item)
            if (itemsWishList.value != null)
                list.addAll(itemsWishList.value!!)
            itemsWishList.value = list
        } else {

            if (itemsWishList.value != null)
                list.addAll(itemsWishList.value!!)

            if(list.contains(item))
                list.remove(item)

            itemsWishList.value = list
        }
        (fragment as ProductListFragment).updateWishList(itemsWishList)
    }

    override fun onProductSelected(
        item: ResultsItem?,
        productType: ProductType
    ) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProductDetailsFragment.newInstance(item, productType))
            .addToBackStack(null)
            .commit()
    }
}
