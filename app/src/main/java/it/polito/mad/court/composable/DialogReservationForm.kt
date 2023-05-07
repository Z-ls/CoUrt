package it.polito.mad.court.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import it.polito.mad.court.dataclass.Court
import it.polito.mad.court.dataclass.Reservation
import it.polito.mad.court.dataclass.Sport
import it.polito.mad.court.dataclass.User
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogReservationForm(res: Reservation, onSave: () -> Unit, onDismiss: () -> Unit) {

//    val newReservation by rememberSaveable { mutableStateOf(res) }
    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                pageCount = 2
            ) { page ->
                when (page) {
                    0 -> {
                        DialogCourtForm(res = res, onSave = onSave, onDismiss = onDismiss)
                    }

                    1 -> {
                        Text("User")
                        Text("Number of players")
                        Text("Skill level")
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (pagerState.canScrollBackward) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = pagerState.canScrollBackward
                ) {
                    Text("Previous")
                }
                Button(
                    onClick = {
                        if (pagerState.canScrollForward) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = pagerState.canScrollForward
                ) {
                    Text("Next")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            onSave()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}


@Composable
fun DialogCourtForm(res: Reservation, onSave: () -> Unit, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text("Court")
//            Text("Date")
//            Text("Time")
//            Text("Duration")
//            Text("Price")
            DialogCourtSelection {}
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .wrapContentWidth(Alignment.CenterHorizontally),
            , horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonDatePicker(res.date) {}
                Spacer(modifier = Modifier.width(16.dp))
                ButtonTimePicker(res.time) {}
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DialogCourtSelection(onClick: () -> Unit) {
    var searchText by remember { mutableStateOf("Search for a court...") }
    var searchResult by remember { mutableStateOf(emptyList<String>()) }
    var isSearchOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { isSearchOpen = true }
            .width(300.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                //TODO: Perform search here
                searchResult = listOf("Result 1 --************----------*********------------*****************", "Result 2", "Result 3")
                isSearchOpen = true
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
        )
        DropdownMenu(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(max = 300.dp),
            expanded = isSearchOpen,
            onDismissRequest = { isSearchOpen = false },
            properties = PopupProperties(focusable = false)
        ) {
            searchResult.forEach { result ->
                DropdownMenuItem(onClick = {
                    onClick()
                    searchText = result
                    isSearchOpen = false
                }, text = { Text(result) })
            }
        }
    }
}


@Preview
@Composable
fun DialogReservationFormPreview() {
    val res = Reservation(
        court = Court(
            name = "Basketball court",
            address = "Via Giuseppe Verdi, 5, 10124 Torino TO",
            city = "Torino",
            country = "Italy",
            sport = Sport(name = "Basketball"),
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
        date = LocalDate.now(),
        time = LocalTime.now(),
        duration = 1,
        price = 10.0,
        status = "pending",
        numPlayers = 8,
        skillLevel = "beginner"

    )
    DialogReservationForm(res, {}, {})
}