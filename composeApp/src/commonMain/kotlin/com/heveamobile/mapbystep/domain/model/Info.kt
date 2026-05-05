package com.heveamobile.mapbystep.domain.model

sealed interface Info {
    val id: String

    data class CountryInfo(
        override val id: String,
        val cca2: String,
        val continents: String,
        val currencies: String,
        val flag: String,
        val languages: String,
        val population: Int,
        val capitals: String,
    ) : Info
}