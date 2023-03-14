package com.example.example

import com.google.gson.annotations.SerializedName


data class Info (

  @SerializedName("descr"      ) var descr     : String? = null,
  @SerializedName("image"      ) var image     : String? = null,
  @SerializedName("img_width"  ) var imgWidth  : Int?    = null,
  @SerializedName("src"        ) var src       : String? = null,
  @SerializedName("src_id"     ) var srcId     : Int?    = null,
  @SerializedName("img_height" ) var imgHeight : Int?    = null

)