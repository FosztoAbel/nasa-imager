import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import main.MainScreen

fun MainViewController() = ComposeUIViewController {
    Navigator(MainScreen())
}