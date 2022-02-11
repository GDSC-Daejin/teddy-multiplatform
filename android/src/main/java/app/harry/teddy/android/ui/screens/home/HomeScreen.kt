package app.harry.teddy.android.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.harry.teddy.android.ui.components.ScoreboardListItem
import app.harry.teddy.android.utils.extract
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import domain.dto.request.ScoreboardRequestParams
import presentation.HomeScreenViewModel
import presentation.HomeScreenViewModelDelegate

@Composable
fun HomeScreen(
    navController: NavController,
    vm: HomeScreenViewModelDelegate = hiltViewModel<HomeScreenViewModel>()
) {

    val (state, effect, event) = vm.extract()

    val swipeRefreshState = rememberSwipeRefreshState(state.isLoading)

    var selectedTab by remember { mutableStateOf(0) }

    var selectedDuration by remember { mutableStateOf(ScoreboardRequestParams.Duration.All) }

    var isDropdownMenuOpened by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTab) {
        event(
            HomeScreenViewModelDelegate.Event.ChangeScoreboardType(
                if (selectedTab == 0) ScoreboardRequestParams.Type.To
                else ScoreboardRequestParams.Type.From
            )
        )
    }

    LaunchedEffect(selectedDuration) {
        isDropdownMenuOpened = false
        event(
            HomeScreenViewModelDelegate.Event.ChangeScoreboardDuration(selectedDuration)
        )
    }

    Scaffold(
        topBar = {
            Surface {
                Column {

                    TopAppBar(
                        backgroundColor = Color.Unspecified,
                        elevation = 0.dp,
                        title = { Text("Teddy Shelf") },
                        actions = {
                            HomeDurationDropdownMenu(
                                modifier = Modifier.width(100.dp),
                                selectedDuration = selectedDuration,
                                isDropdownMenuOpened = isDropdownMenuOpened,
                                onDropdownMenuOpenStateChanged = { isDropdownMenuOpened = it },
                                onSelectedDurationChanged = { selectedDuration = it }
                            )
                        },
                        contentPadding = rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars)
                    )

                    TabRow(
                        selectedTabIndex = selectedTab,
                        divider = {}
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("받은 사람") }
                        )

                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("보낸 사람") }
                        )
                    }

                }
            }
        }
    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize().padding(it),
            state = swipeRefreshState,
            onRefresh = {
                event(HomeScreenViewModelDelegate.Event.Refresh)
            }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(state.scoreboards) { scoreboard ->
                    ScoreboardListItem(scoreboard) {
                        navController.navigate("detail/${it.username}")
                    }
                }

                item {
                    Spacer(modifier = Modifier.navigationBarsHeight(20.dp))
                }
            }
        }
    }

}