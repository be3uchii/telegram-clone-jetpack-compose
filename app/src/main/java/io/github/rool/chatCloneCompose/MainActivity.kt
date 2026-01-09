package io.github.rool.chatCloneCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.github.rool.chatCloneCompose.core.navigation.ChatCloneScreens
import io.github.rool.chatCloneCompose.core.ui.theme.ChatCloneComposeTheme
import io.github.rool.chatCloneCompose.core.ui.theme.TelegramBlue40
import io.github.rool.chatCloneCompose.features.chatGroup.ChatGroupScreen
import io.github.rool.chatCloneCompose.features.lobby.LobbyScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ChatCloneComposeTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = TelegramBlue40
                    )
                }
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ChatCloneScreens.Lobby.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(
                        route = ChatCloneScreens.Lobby.route,
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
                        }
                    ) {
                        LobbyScreen(navController)
                    }
                    composable(
                        route = ChatCloneScreens.ChatGroup.route,
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
                        }
                    ) {
                        ChatGroupScreen(navController = navController, viewModel = hiltViewModel())
                    }
                }
            }
        }
    }
}
