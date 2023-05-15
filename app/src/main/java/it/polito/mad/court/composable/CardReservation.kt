package it.polito.mad.court.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.mad.court.R
import it.polito.mad.court.dataclass.Court
import it.polito.mad.court.dataclass.DateString
import it.polito.mad.court.dataclass.Reservation
import it.polito.mad.court.dataclass.TimeString
import it.polito.mad.court.dataclass.User
import java.time.LocalDate

@Composable
fun CardReservation(res: Reservation) {

    var isExpanded by remember { mutableStateOf(false) }
    val onClick = { isExpanded = !isExpanded }
    val alpha by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutLinearInEasing
        )
    )

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = cardColors(
            containerColor = Color(0xEDF2F2F2),
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.basketball_indoor),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = res.date.toString() + " " + res.time.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = res.court.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = res.court.address,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                ReservationDetailRow("Durations", "${res.duration} hours")
                ReservationDetailRow("Players", "${res.numPlayers} / ${res.maxPlayers}")
                AnimatedVisibility(
                    visible = isExpanded,
                    Modifier.alpha(alpha)
                ) {
                    CardReservationAdditionalInfo(res)
                }
            }
        }
    }
}

@Composable
fun CardReservationAdditionalInfo(res: Reservation) {
    Column {
        ReservationDetailRow("Price", "${res.price} Euro")
        ReservationDetailRow(
            "Level",
            listOf("Beginner", "Intermediate", "Advanced")[res.skillLevel]
        )
    }
}

@Composable
fun ReservationDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun CardReservationPreview() {
    val res = Reservation(
        court = Court(
            name = "Basketball court",
            address = "Via Giuseppe Verdi, 5, 10124 Torino TO",
            city = "Torino",
            country = "Italy",
            sport = "Basketball",
            price = 10.0,
            rating = 4.5
        ),
        user = User(
            email = "johndoe@gmail.com",
            firstname = "John",
            lastname = "doe",
            nickname = "J-Doe",
            gender = "male",
            birthdate = LocalDate.of(1993, 5, 5),
            height = 1.83,
            weight = 75.0,
            city = "Torino",
            country = "Italy",
            bio = "I like playing basketball",
            phone = "110-120-12315"
        ),
        date = DateString("05-05-2021"),
        time = TimeString("10:00"),
        duration = 1,
        price = 10,
        minPlayers = 1,
        maxPlayers = 10,
        numPlayers = 8,
        skillLevel = 0
    )
    CardReservation(res)
}