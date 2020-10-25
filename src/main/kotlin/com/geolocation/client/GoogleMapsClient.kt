package com.geolocation.client

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GoogleMapsClient(webClient: WebClient.Builder) {

    private val log: Logger = LoggerFactory.getLogger(GoogleMapsClient::class.java)
    private val webClientBuilder : WebClient = webClient.baseUrl("https://maps.googleapis.com/maps/api/geocode").build()

    @Value("\${envs.appkey}")
    lateinit var appKey: String

    fun getAddress(address : String) : Mono<JsonNode> {
        return webClientBuilder
                .get()
                .uri("/json?address=$address&key=$appKey")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode::class.java).doOnSuccess{
                    log.info("Success operation")
                }
    }
}