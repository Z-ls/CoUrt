package it.polito.mad.court.dataclass

import java.time.LocalDate
import java.time.LocalTime

data class Reservation(
    val court: Court,
    val user: User,
    val date: LocalDate,
    val time: LocalTime,
    val duration: Int,
    val price: Double,
    val status: String,
    val numPlayers: Int,
    val skillLevel: String,
)
