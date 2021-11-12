package com.cts.ezcartapp.domain.model

import com.cts.ezcartapp.data.entities.OrdersData
import com.google.gson.annotations.SerializedName

data class OrdersList(@SerializedName("data") val data : List<OrdersData>)
