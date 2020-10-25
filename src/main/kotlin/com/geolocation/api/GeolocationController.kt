package com.geolocation.api

import com.geolocation.domain.Distance
import com.geolocation.domain.Geolocation
import com.geolocation.service.GeolocationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequestMapping("/v1/geolocation")
@RestController
class GeolocationController {

    private val log: Logger = LoggerFactory.getLogger(GeolocationController::class.java)

    @Autowired
    private lateinit var geolocationService: GeolocationService

    @PostMapping
    fun getBetterRoutes(@RequestBody listAddress : Geolocation) : Mono<Distance> {
        log.info("[API-CALCULATE-GEOLOCATION] starting calculation geolocation")
        return geolocationService.processGeolocation(listAddress.addressList)
    }
}