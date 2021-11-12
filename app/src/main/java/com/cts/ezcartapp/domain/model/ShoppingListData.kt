package com.cts.ezcartapp.domain.model

import com.cts.ezcartapp.data.entities.ShoppingData
import com.google.gson.annotations.SerializedName

data class ShoppingListData(@SerializedName("data") val data : List<ShoppingData>)
