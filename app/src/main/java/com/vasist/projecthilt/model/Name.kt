package com.vasist.projecthilt.model

import java.io.Serializable

data class Name(
    val first: String,
    val last: String,
    val title: String
): Serializable