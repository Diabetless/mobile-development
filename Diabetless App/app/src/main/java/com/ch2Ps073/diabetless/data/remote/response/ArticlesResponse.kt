package com.ch2Ps073.diabetless.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("article")
	val article: List<ArticleItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ArticleItem(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("postDate")
	val postDate: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
