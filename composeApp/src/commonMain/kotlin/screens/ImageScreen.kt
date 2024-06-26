package screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.NasaApiResponse
import shared.FlippableCard

@Composable
fun ImageScreen(
    image: NasaApiResponse,
    backVisible: Boolean,
    forwardVisible: Boolean,
    onRandomPicture: () -> Unit,
    onShowDialog: () -> Unit,
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = image.title,
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = image.date,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable {
                        onShowDialog()
                    }
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onRandomPicture()
                    },
                imageVector = Icons.Default.Refresh,
                contentDescription = "Random picture",
                tint = Color.White,
            )
        }
        FlippableCard(
            url = image.hdurl ?: image.url,
            description = image.explanation ?: "No explanation added.",
            backVisible = backVisible,
            forwardVisible = forwardVisible,
            onBackPressed = onBackPressed,
            onForwardPressed = onForwardPressed
        )
    }
}