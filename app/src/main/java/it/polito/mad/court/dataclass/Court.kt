package it.polito.mad.court.dataclass

data class Court(
    val rating: Double = 0.0,
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val openingTime: String = "",
    val closingTime: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val sport: String = "",
    val description: String = "",
    val isOutdoor: Boolean = false,
)
