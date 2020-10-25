# Geolocation APi Project
### Description:

This API basically consists of calculating the geolocation between two points.

### Modes of use:

To execute this API just make an HTTP request to:

POST https://geolocation-api.herokuapp.com/v1/geolocation and pass the colon in the request body so that they can be calculated, example:

{
"addressList": ["test address one", "test address two"]
}

Remembering that it is enough to pass the address in a string array separated by empty spaces so that we can adjust the URI to access the Google Maps API correctly.

A correct request must present the following feedback:

### HTTP Response 200 (OK)

### Response body:

{
    "distance": 1.4142135623730951
}

You will find source code for this application in a public repository at the following github address: https://github.com/roger-amorim-dv/geolocation-api

### Technical reasons and decisions

* Kotlin
* Spring WebFlux (Reactive Programming)
* Spring Boot

I decided to make this API using Kotlin as a language and in a reactive way. I tried to uncouple as much as possible, and write a simple and eligible and performing code.
