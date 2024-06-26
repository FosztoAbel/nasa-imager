package shared

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.launch

@Composable
fun FlippableCard(
    url: String,
    description: String,
    backVisible: Boolean,
    forwardVisible: Boolean,
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val frontVisible = rotation.value < 90

    Column {
        Card(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .weight(1f)
            .clickable {
                coroutineScope.launch {
                    if (frontVisible) {
                        rotation.animateTo(180f)
                    } else {
                        rotation.animateTo(0f)
                    }
                }
            }
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
                rotationY = rotation.value
            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = if(frontVisible) Color.Black else Color.DarkGray,
        ) {
            if (frontVisible) {
                if(url.contains("youtube", ignoreCase = true)) {
                    val state = rememberWebViewState(url)

                    WebView(state, modifier = Modifier.fillMaxSize())
                } else {
                    SubcomposeAsyncImage(
                        model = url,
                        modifier = Modifier
                            .scale(1.2f),
                        contentDescription = null,
                        loading = {
                            Box(
                                modifier = Modifier.size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color.Blue,
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    )
                }
            } else {
                Text(
                    text = description,
                    style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier
                        .padding(8.dp)
                        .graphicsLayer {
                            rotationY = 180f
                        }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = if (backVisible) Arrangement.SpaceBetween else Arrangement.End
        ) {
            if (backVisible) {
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            onBackPressed()
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
            if (forwardVisible) {
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            onForwardPressed()
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Forward",
                    tint = Color.White,
                )
            }
        }
    }
}
