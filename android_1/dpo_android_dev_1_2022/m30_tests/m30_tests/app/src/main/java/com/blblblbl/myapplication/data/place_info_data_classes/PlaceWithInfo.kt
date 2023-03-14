package com.example.example

import com.google.gson.annotations.SerializedName


data class PlaceWithInfo (

  @SerializedName("kinds"     ) var kinds     : String?  = null,
  @SerializedName("sources"   ) var sources   : Sources? = Sources(),
  @SerializedName("bbox"      ) var bbox      : Bbox?    = Bbox(),
  @SerializedName("point"     ) var point     : Point?   = Point(),
  @SerializedName("osm"       ) var osm       : String?  = null,
  @SerializedName("otm"       ) var otm       : String?  = null,
  @SerializedName("xid"       ) var xid       : String?  = null,
  @SerializedName("name"      ) var name      : String?  = null,
  @SerializedName("wikipedia" ) var wikipedia : String?  = null,
  @SerializedName("image"     ) var image     : String?  = null,
  @SerializedName("wikidata"  ) var wikidata  : String?  = null,
  @SerializedName("rate"      ) var rate      : String?  = null,
  @SerializedName("info"      ) var info      : Info?    = Info()

)