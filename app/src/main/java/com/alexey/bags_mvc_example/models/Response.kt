package com.alexey.bags_mvc_example.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Response(

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
) :Serializable