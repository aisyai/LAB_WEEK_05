package com.example.lab_week_05.model

import com.squareup.moshi.Json

data class CatBreedData(
    val id: String,
    val name: String,
    val origin: String? = null,
    val description: String? = null
)