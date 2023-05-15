package it.polito.mad.court

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import it.polito.mad.court.dataclass.Reservation
import it.polito.mad.court.dataclass.User
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalDateSerializer : JsonSerializer<LocalDate> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return JsonPrimitive(src?.format(formatter))
    }
}

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDate.parse(json?.asString, formatter)
    }
}

class LocalTimeSerializer : JsonSerializer<LocalTime> {
    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return JsonPrimitive(src?.format(formatter))
    }
}

class LocalTimeDeserializer : JsonDeserializer<LocalTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.parse(json?.asString, formatter)
    }
}

@Suppress("unused")
class DbCourt {
    private val gson = Gson().newBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer())
        .create()
    private var db = FirebaseDatabase.getInstance()

    private fun getReference(
        name: String,
    ): DatabaseReference {
        return db.getReference(name)
    }

    fun getUsers(
        callback: (List<User>) -> Unit,
    ) {
        val refs = getReference(
            "users",
        )
        refs.get().addOnSuccessListener {
            val users = mutableListOf<User>()
            for (data in it.children) {
                users.add(gson.fromJson(data.value.toString(), User::class.java)!!)
            }
            callback(users)
        }
    }

    fun getUserByEmail(
        email: String,
        callback: (User) -> Unit,
    ) {
        val refs = getReference(
            "users",
        )
        refs.orderByChild("email").equalTo(email).get().addOnSuccessListener {
            callback(gson.fromJson(it.value.toString(), User::class.java)!!)
        }
    }

    fun addUser(
        user: User,
    ) {
        val refs = getReference(
            "users",
        )
        refs.child(user.email).setValue(gson.toJson(user))
    }

    fun updateUser(
        user: User,
    ) {
        val refs = getReference(
            "users",
        )
        refs.child(user.email).setValue(gson.toJson(user))
    }

    fun deleteUser(
        user: User,
    ) {
        val refs = getReference(
            "users",
        )
        refs.child(user.email).removeValue()
    }

    fun getReservations(
        callback: (List<Reservation>) -> Unit,
    ) {
        val refs =
            getReference(
                "reservations",
            )
        refs.get().addOnSuccessListener {
            val reservations = mutableListOf<Reservation>()
            for (data in it.children) {
                val res = gson.fromJson(data.value.toString(), Reservation::class.java)!!
                res.id = data.key!!
                reservations.add(res)
            }
            callback(reservations)
        }
    }

    fun getReservationById(
        id: String,
        callback: (Reservation) -> Unit,
    ) {
        val refs =
            getReference(
                "reservations",
            )
        refs.child(id).get().addOnSuccessListener {
            callback(gson.fromJson(it.value.toString(), Reservation::class.java)!!)
        }
    }

    fun addReservation(
        reservation: Reservation
    ) {
        val refs =
            getReference(
                "reservations",
            )
        refs.push().setValue(gson.toJson(reservation))
    }

    fun updateReservation(
        reservation: Reservation,
    ) {
        val refs =
            getReference(
                "reservations",
            )
        refs.child(reservation.id).setValue(gson.toJson(reservation))
    }

    fun deleteReservation(
        reservation: Reservation,
    ) {
        val refs =
            getReference(
                "reservations",
            )
        refs.child(reservation.id).removeValue()
    }
}