package it.polito.mad.court.dataclass

data class Sport(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val isOutdoor: Boolean = false,
    val isTeamSport: Boolean = false,
    val teamSize: Int = 1,
    val numPlayer: Int = 1,
)
