package pl.kacper.misterski.rangestats.ui.main


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.kacper.misterski.rangestats.ui.common.bottom_bar.AppNavigationBar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MainScreen(onAction: (MainAction) -> Unit) {
    Scaffold(
        content = { padding ->

            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                Text("Main screen", modifier = Modifier.align(Alignment.Center))

                Button(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = {
                        onAction(MainAction.ShowBottomSheet)
                    }) {
                    Text("Show Login")
                }


            }

        },
        bottomBar = { AppNavigationBar(modifier = Modifier.fillMaxWidth(), bottomBarAction = {}) }
    )

}


