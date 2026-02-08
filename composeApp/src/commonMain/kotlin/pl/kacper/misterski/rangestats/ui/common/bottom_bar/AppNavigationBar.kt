package pl.kacper.misterski.rangestats.ui.common.bottom_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ModelTraining
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    bottomBarAction: (AppNavigationBarAction) -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, // This helps distribute space
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarAction(
                    imageVector = Icons.Default.ModelTraining,
                    title = "Train",
                    onClick = { bottomBarAction(AppNavigationBarAction.Train) },
                )
                BottomBarAction(
                    imageVector = Icons.Default.BarChart,
                    title = "Stats",
                    onClick = { bottomBarAction(AppNavigationBarAction.Stats) },
                )
                BottomBarAction(
                    imageVector = Icons.Default.Settings,
                    title = "Settings",
                    onClick = { bottomBarAction(AppNavigationBarAction.Settings) },
                )
                BottomBarAction(
                    imageVector = Icons.Default.Info,
                    title = "About",
                    onClick = { bottomBarAction(AppNavigationBarAction.About) },
                )
            }
        },
    )
}


@Composable
private fun BottomBarAction(
    modifier: Modifier = Modifier.size(60.dp),
    imageVector: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector, contentDescription = title)
            Text(text = title, fontSize = 12.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AppNavigationBarPreview(){
    MaterialTheme{
        AppNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            bottomBarAction = { }
        )
    }
}