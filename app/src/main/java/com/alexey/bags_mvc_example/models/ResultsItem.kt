package com.alexey.bags_mvc_example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ResultsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("listing_id")
	val listingId: Int? = null,

	@field:SerializedName("size_W")
	val sizeW: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("currency_code")
	val currencyCode: String? = null,

	@field:SerializedName("colours_2")
	val colours2: String? = null,

	@field:SerializedName("ending_tsz")
	val endingTsz: Int? = null,

	@field:SerializedName("colours_1")
	val colours1: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("last_modified_tsz")
	val lastModifiedTsz: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("creation_tsz")
	val creationTsz: Int? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("size_H")
	val sizeH: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("original_creation_tsz")
	val originalCreationTsz: Int? = null,

	@field:SerializedName("size_D")
	val sizeD: String? = null
) : Serializable