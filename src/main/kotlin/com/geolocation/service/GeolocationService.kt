package com.geolocation.service

import com.fasterxml.jackson.databind.JsonNode
import com.geolocation.client.GoogleMapsClient
import com.geolocation.domain.Distance
import com.geolocation.domain.Location
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class GeolocationService {

    @Autowired
    private lateinit var googleMapsClient: GoogleMapsClient

    private val latJsonNode = "/results/geometry/location/lat"
    private val lngJsonNode = "/results/geometry/location/lng"

    fun processGeolocation(addressList: List<String>) : Mono<Distance> {
        return prepareCoordinates(validateAddress(addressList))
    }

    private fun validateAddress(addressList: List<String>) : Flux<Location> {
        return Flux.fromIterable(addressList).flatMap {
            Flux.merge( getGeolocation( formatterAddress(it) ) )
        }
    }

    private fun formatterAddress(address: String) : String {
        return address.replace(" ", "+", false)
    }

    private fun getGeolocation(address: String) : Mono<Location> {
        return parserJsonNode(googleMapsClient.getAddress(address))
    }

    private fun parserJsonNode(jsonNodeMono: Mono<JsonNode>) : Mono<Location> {
        return jsonNodeMono.flatMap {
            Mono.just( Location(it.at(latJsonNode).asDouble(), it.at(lngJsonNode).asDouble()) )
        }
    }

    private fun prepareCoordinates(fluxLocation: Flux<Location>) : Mono<Distance> {
        var latX = 1.0
        var latY = 1.0
        var lngX = 1.0
        var lngY = 1.0

        fluxLocation.collectList().map {
            val addressOne = it[0]
            val addressTwo = it[1]
            latX = addressOne.lat
            latY = addressTwo.lat
            lngX = addressOne.lng
            lngY = addressTwo.lng
        }.subscribe()

        return calculateEuclidianaDistance(latX, lngX, latY, lngY)
    }

    private fun calculateEuclidianaDistance(latOne : Double, lngOne : Double, latTwo : Double, lngTwo : Double) : Mono<Distance> {
        return Mono.just( Distance( sqrt( latTwo.pow(lngTwo) + latOne.pow(lngOne)) ) )
    }
}