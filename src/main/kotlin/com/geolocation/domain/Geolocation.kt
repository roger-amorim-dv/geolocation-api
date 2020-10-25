package com.geolocation.domain

data class Geolocation (
    val addressList: List<String>
)

data class Results (
    val geometry: List<Geometry>?
)

data class Geometry (
    val location : Location?
)

data class Location (
    val lat : Double,
    val lng : Double
)

data class Distance (
    val distance : Double
)