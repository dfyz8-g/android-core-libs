package com.cren90.android.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginatedRecordsList<T: Any>(
    @Json(name = "current_page")
    val currentPage: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_records")
    val totalRecords: Int,
    @Json(name = "records")
    val records: List<T>
)
