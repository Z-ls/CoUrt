package it.polito.mad.court.dataclass

data class Court(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val phone: String,
    val email: String,
    val website: String,
    val openingTime: String,
    val closingTime: String,
    val price: Double,
    val image: String,
    val sport: Sport,
    val description: String,
    val isOutdoor: Boolean,
)
