package it.polito.mad.court.dataclass

import java.time.LocalDate

data class User(
    var email: String = "",
    var image: String = "",
    var firstname: String = "",
    var lastname: String = "",
    var nickname: String = "",
    var gender: String = "",
    var birthdate: LocalDate = LocalDate.now(),
    var height: Double = 0.0,
    var weight: Double = 0.0,
    var phone: String = "",
    var city: String = "",
    var country: String = "",
    var bio: String = "",
    var sportList: List<Sport> = listOf(),
    var favoriteCourtList: List<Court> = listOf(),
) {
    fun save() {
        TODO("Not yet implemented")
    }
}
