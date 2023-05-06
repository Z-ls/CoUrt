package it.polito.mad.court.dataclass

data class Sport(
    val name: String,
    val image: String,
    val description: String,
    val isOutdoor: Boolean,
    val isTeamSport: Boolean,
    val teamSize: Int,
    val numPlayer: Int,
)
