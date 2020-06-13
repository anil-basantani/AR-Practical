package com.andy_dev.arpractical.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FactsResponse(
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("rows") @Expose var rows: List<Facts>? = null
) : Serializable