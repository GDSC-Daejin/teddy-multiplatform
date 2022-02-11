package app.harry.teddy.android.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.harry.teddy.android.ui.components.UserStatsGivenOrReceivedListItem
import app.harry.teddy.android.utils.extract
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import presentation.DetailScreenViewModel
import presentation.DetailScreenViewModelDelegate

@Composable
fun DetailScreen(
    navController: NavController,
    id: String,
    vm: DetailScreenViewModelDelegate = hiltViewModel<DetailScreenViewModel>()
) {

    val (state, effect, event) = vm.extract()

    LaunchedEffect(true) {
        event(DetailScreenViewModelDelegate.Event.LoadUser(id))
    }

    val swipeRefreshState = rememberSwipeRefreshState(state.isTotalContentLoading)

    val pagerState = rememberPagerState(0)

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column {

                TopAppBar(
                    backgroundColor = MaterialTheme.colors.surface,
                    title = {},
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Rounded.ArrowBack, null)
                        }
                    },
                    contentPadding = rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars)
                )

                DetailScreenTopHeader(state.user)

                TabRow(
                    modifier = Modifier.height(56.dp),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }
                ) {
                    Tab(pagerState.currentPage == 0, {
                        coroutineScope.launch { pagerState.animateScrollToPage(0) }
                    }) { Text("내가 준 멤버들") }

                    Tab(pagerState.currentPage == 1, {
                        coroutineScope.launch { pagerState.animateScrollToPage(1) }
                    }) { Text("나에게 준 멤버들") }
                }
            }

        }
    ) {

        SwipeRefresh(state = swipeRefreshState, modifier = Modifier.padding(it).fillMaxSize(),
            onRefresh = { event(DetailScreenViewModelDelegate.Event.Refresh) }) {

            HorizontalPager(2, state = pagerState) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(
                        if (it == 0) state.userStats?.given ?: emptyList()
                        else state.userStats?.received ?: emptyList()
                    ) {
                        UserStatsGivenOrReceivedListItem(it) {
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

}