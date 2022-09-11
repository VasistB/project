package com.vasist.projecthilt.model

import java.io.Serializable

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
): Serializable