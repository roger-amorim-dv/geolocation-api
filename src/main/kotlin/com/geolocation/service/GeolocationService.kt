package com.geolocation.service

import com.geolocation.client.GoogleMapsClient
import com.geolocation.domain.BuildLocations
import com.geolocation.domain.Distance
import com.geolocation.domain.Location
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.math.sqrt

@Service
class GeolocationService {

    @Autowired
    private lateinit var googleMapsClient: GoogleMapsClient

    fun processGeolocation(addressList: List<String>) : Mono<Distance> {
        return prepareCoordinates( validateAddress( addressList ) )
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
        return googleMapsClient.getAddress(address).flatMap {
            Mono.just( it.results[0].geometry.location )
        }
    }

    private fun prepareCoordinates(fluxLocation: Flux<Location>) : Mono<Distance> {
        return fluxLocation.collectList().map { BuildLocations(it[0], it[1]) }
                .flatMap {
                    calculateEuclidianaDistance(
                        it.firstLocation.lat,
                        it.firstLocation.lng,
                        it.lastLocation.lat,
                        it.lastLocation.lng
            )
        }
    }

    private fun calculateEuclidianaDistance(latOne : Double, lngOne : Double, latTwo : Double, lngTwo : Double) : Mono<Distance> {
        return Mono.just( Distance( sqrt( (latOne - lngOne) * (latOne - lngOne) + (latTwo - lngTwo) * (latTwo - lngTwo) ) ) )
    }
}