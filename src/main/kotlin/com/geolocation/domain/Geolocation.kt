package com.geolocation.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class Geolocation (
    val addressList: List<String>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Response(
    val results: List<Results>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Results (
    val geometry: Geometry
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Geometry (
    val location: Location
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location (
    val lat : Double,
    val lng : Double
)

data class BuildLocations (
    val firstLocation: Location,
    val lastLocation: Location
)

data class Distance (
    val distance : Double
)