package com.example.lab_week_05.model

data class ImageData(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<CatBreedData> = emptyList()
)