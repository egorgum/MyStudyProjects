package com.example.example

import com.google.gson.annotations.SerializedName


data class Bbox (

  @SerializedName("lat_max" ) var latMax : Double? = null,
  @SerializedName("lat_min" ) var latMin : Double? = null,
  @SerializedName("lon_max" ) var lonMax : Double? = null,
  @SerializedName("lon_min" ) var lonMin : Double? = null

)